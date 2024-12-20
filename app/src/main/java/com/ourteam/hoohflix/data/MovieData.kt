package com.ourteam.hoohflix.data

import com.ourteam.hoohflix.model.MovieItem
import com.ourteam.hoohflix.R


fun recentMoviesData(): List<MovieItem> {
    return listOf(
        MovieItem(
            id = 1,
            title = "The Wild Robot",
            overview = "After a shipwreck, an intelligent robot called Roz is stranded on an uninhabited island. To survive the harsh environment, Roz bonds with the island's animals and cares for an orphaned baby goose.",
            genre = "Animation, Action, Science Fiction, Family",
            poster_local = R.drawable.recent_twr,
            poster_url = "",
            release_date = "2005",
            vote_average = 8.6,
            vote_count = 5000
        ),
        MovieItem(
            id = 2,
            title = "Terrifier 3",
            overview = "Five years after surviving Art the Clown's Halloween massacre, Sienna and Jonathan are still struggling to rebuild their shattered lives. As the holiday season approaches, they try to embrace the Christmas spirit and leave the horrors of the past behind. But just when they think they're safe, Art returns, determined to turn their holiday cheer into a new nightmare. The festive season quickly unravels as Art unleashes his twisted brand of terror, proving that no holiday is safe.",
            genre = "Horror, Thriller",
            poster_local = R.drawable.recent_t3,
            poster_url = "",
            release_date = "2005",
            vote_average = 7.2,
            vote_count = 5000
        ),
        MovieItem(
            id = 3,
            title = "Transformers One",
            overview = "The untold origin story of Optimus Prime and Megatron, better known as sworn enemies, but once were friends bonded like brothers who changed the fate of Cybertron forever.",
            genre = "Animation, Science Fiction, Adventure",
            poster_local = R.drawable.recent_to,
            poster_url = "",
            release_date = "2005",
            vote_average = 8.1,
            vote_count = 5000
        )
    )
}