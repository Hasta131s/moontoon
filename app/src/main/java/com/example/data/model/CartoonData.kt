package com.example.data.model

import android.content.Context
import com.example.R
import java.io.BufferedReader
import java.io.InputStreamReader

object CartoonData {
    fun getShows(context: Context): List<CartoonShow> {
        val showsMap = mutableMapOf<String, MutableList<Episode>>()
        
        try {
            val inputStream = context.resources.openRawResource(R.raw.playlist)
            val reader = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            
            var currentLogo = ""
            var currentSeries = ""
            var currentEpisodeTitle = ""

            while (reader.readLine().also { line = it } != null) {
                val currentLine = line!!.trim()
                if (currentLine.startsWith("#EXTINF:")) {
                    // Extract logo
                    val logoMatch = Regex("""tvg-logo="([^"]+)"""").find(currentLine)
                    currentLogo = logoMatch?.groupValues?.get(1) ?: ""

                    // Extract Series Name and Episode Title
                    val titlePart = currentLine.substringAfterLast(",")
                    if (titlePart.contains(" - ")) {
                        currentSeries = titlePart.substringBefore(" - ").trim()
                        currentEpisodeTitle = titlePart.substringAfter(" - ").trim()
                    } else {
                        currentSeries = "Diğer"
                        currentEpisodeTitle = titlePart.trim()
                    }
                } else if (currentLine.isNotEmpty() && !currentLine.startsWith("#")) {
                    // It's the URL line
                    val url = currentLine
                    
                    if (currentSeries.isNotEmpty() && url.isNotEmpty()) {
                        val list = showsMap.getOrPut(currentSeries) { mutableListOf() }
                        val seasonNumber = 1
                        val episodeNumber = list.size + 1
                        list.add(Episode(currentEpisodeTitle, url, currentLogo, seasonNumber, episodeNumber))
                    }
                }
            }
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        val resultList = mutableListOf<CartoonShow>()
        for ((seriesName, episodes) in showsMap) {
            resultList.add(
                CartoonShow(
                    title = seriesName,
                    searchKeyword = seriesName,
                    genres = listOf("#çizgifilm", "#eğlence", "#macera"),
                    description = "$seriesName çizgi dizisinin en sevilen bölümleri.",
                    rating = "8.0",
                    years = "2020-",
                    seasonsCount = 1,
                    episodes = episodes
                )
            )
        }
        return resultList
    }
}
