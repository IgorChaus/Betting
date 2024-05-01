package com.example.betting.presentation.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.betting.domain.models.League
import com.example.betting.domain.models.Player
import com.example.betting.domain.repositories.AppRepository
import com.example.betting.presentation.adapter.PlayerListAdapter
import com.example.betting.presentation.states.State
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.lang.Integer.min
import javax.inject.Inject

class DiscoverViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _state = BehaviorSubject.create<State>()
    val state: Observable<State>
        get() = _state.hide()

    private var leagueList: List<League>? = null
    private val playerList = arrayListOf<PlayerListAdapter.AdapterItems>()
    private var strSearch: String = EMPTY

    init{
        getPlayersFromAllLeagues()
    }

    fun setContentListState() {
        _state.onNext(State.ContentList(playerList))
    }

    fun setActivateSearch() {
        _state.onNext(State.ActivateSearch)
        if (this.strSearch.isNotEmpty()){
            val strSearch = this.strSearch
            this.strSearch = EMPTY
            setFilteredListState(strSearch)
        }
    }

    fun setFilteredListState(strSearch: String) {
        if (this.strSearch == strSearch){
            return
        }
        this.strSearch = strSearch
        val filteredList = if (strSearch.isNotEmpty()){
            searchPlayer(strSearch)
        } else {
            playerList
        }
        _state.onNext(State.FilteredList(filteredList))
    }

    fun setSearchResultState(strSearch: String) {
        if (strSearch.isEmpty()) {
            _state.onNext(State.ContentList(playerList))
        } else {
            val searchList = searchPlayer(strSearch)
            if (searchList.isNotEmpty()) {
                _state.onNext(State.ResultSearch(searchList))
            } else {
                _state.onNext(State.NothingFound)
            }
        }
    }

    private fun searchPlayer(strSearch: String) = playerList.filterIsInstance<Player>()
        .filter {
            it.firstName?.contains(strSearch, ignoreCase = true) ?: false ||
                    it.lastName?.contains(strSearch, ignoreCase = true) ?: false
        }

    private fun getLeagues(): Single<List<League>> {
        return repository.getLeagues(LEAGUE_NAME, "2023")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { leagues ->
                leagueList = leagues
            }
            .doOnError { error ->
                handleException(error)
            }
    }

    private fun getPlayers(leagueItem: League): Single<List<Player>> {
        val leagueId = leagueItem.id.toString()
        return repository.getPlayers(leagueId, "2023", "1")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { response ->

                playerList.add(
                    League(
                        id = leagueItem.id,
                        name = leagueItem.name,
                        logo = leagueItem.logo
                    )
                )

                val updatedPlayers = response.map { player ->
                    player.copy(leagueName = leagueItem.name, leagueLogo = leagueItem.logo)
                }
                playerList.addAll(updatedPlayers.take(LIMIT_LIST))
                updatedPlayers
            }
            .doOnError { error ->
                handleException(error)
            }
    }

    fun getPlayersFromAllLeagues() {
        _state.onNext(State.Loading(data = playerList, progress = 0, progressVisible = View.VISIBLE))
        disposables.add(
            getLeagues()
                .flatMapObservable { leagues ->
                    Observable.fromIterable(leagues.indices.zip(leagues).take(min(NUMBER_LEAGUES, leagues.size)))
                }
                .concatMapEager { indexedLeague ->
                    val index = indexedLeague.first
                    val league = indexedLeague.second
                    val totalLeagues = min(NUMBER_LEAGUES, leagueList?.size ?:1)
                    val progress = (index + 1) * 100 / totalLeagues
                    _state.onNext(State.Loading(data = playerList, progress = progress, progressVisible = View.VISIBLE))
                    getPlayers(league).toObservable()
                }
                .toList()
                .subscribe(
                    { _ ->
                        _state.onNext(State.Loading(data = playerList, progress = 100, progressVisible = View.GONE))
                        _state.onNext(State.ContentList(playerList))
                    },
                    { error ->
                        handleException(error)
                    }
                )
        )
    }

    private fun handleException(throwable: Throwable?) {
        Log.i("MyTag", "Exception $throwable")
        _state.onNext(State.Error)
    }

    fun clearDisposables() {
        disposables.clear()
    }

    companion object {
        const val LEAGUE_NAME = "premier league"
        const val LIMIT_LIST = 10
        const val EMPTY = ""
        const val NUMBER_LEAGUES = 3
    }

}

