package com.slashgil.comics.data.repository.impl

import com.slashgil.comics.data.local.contract.ComicsLocalDataSource
import com.slashgil.comics.data.mapper.toDomain
import com.slashgil.comics.data.remote.contract.ComicsRemoteDataSource
import com.slashgil.comics.data.repository.contract.ComicsRepositoryContract
import com.slashgil.comics.domain.model.Appearance
import com.slashgil.comics.domain.model.Biography
import com.slashgil.comics.domain.model.Character
import com.slashgil.comics.domain.model.Connections
import com.slashgil.comics.domain.model.Powerstats
import com.slashgil.comics.domain.model.Work
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ComicsRepositoryImpl @Inject constructor(
    private val remoteDataSource: ComicsRemoteDataSource,
    private val localDataSource: ComicsLocalDataSource
) : ComicsRepositoryContract {

    override suspend fun getCharacters(
        query: String?,
        publisher: String?
    ): Result<List<Character>> = withContext(Dispatchers.IO) {
        runCatching {
            val q = query?.trim().orEmpty()
            val pub = publisher?.trim().orEmpty()

            val localList = localDataSource.searchCharacters(q, pub)

            if (localList.isNotEmpty()) {
                localList
            } else {
                val searchQ = q.ifEmpty { "a" }
                val response = remoteDataSource.searchCharacters(searchQ)
                val networkList = response.results?.map { it.toDomain() } ?: emptyList()

                if (networkList.isNotEmpty()) {
                    localDataSource.saveCharacters(networkList)
                    localDataSource.searchCharacters(q, pub)
                } else {
                    val sampleFiltered = filterByPublisher(getFallbackCharacters(q), pub)
                    localDataSource.saveCharacters(sampleFiltered)
                    sampleFiltered
                }
            }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = {
                val fallback = filterByPublisher(getFallbackCharacters(query), publisher)
                Result.success(fallback)
            }
        )
    }

    override suspend fun getCharacterDetails(characterId: String): Result<Character> = withContext(Dispatchers.IO) {
        runCatching {
            val localChar = localDataSource.getCharacterById(characterId)
            if (localChar != null && localChar.biography.fullName.isNotBlank()) {
                localChar
            } else {
                val dto = remoteDataSource.getCharacterDetails(characterId)
                if (dto.id != null) {
                    val domainChar = dto.toDomain()
                    localDataSource.saveCharacters(listOf(domainChar))
                    domainChar
                } else {
                    sampleCharacters.find { it.id == characterId } ?: sampleCharacters.first()
                }
            }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = {
                val fallback = sampleCharacters.find { c -> c.id == characterId } ?: sampleCharacters.first()
                Result.success(fallback)
            }
        )
    }

    private fun filterByPublisher(list: List<Character>, publisher: String?): List<Character> {
        val pub = publisher?.trim().orEmpty()
        if (pub.isBlank() || pub.equals("All", ignoreCase = true)) return list
        return list.filter { it.biography.publisher.contains(pub, ignoreCase = true) }
    }

    private fun getFallbackCharacters(query: String?): List<Character> {
        val q = query?.trim().orEmpty()
        return if (q.isNotBlank()) {
            sampleCharacters.filter {
                it.name.contains(q, ignoreCase = true) ||
                it.biography.fullName.contains(q, ignoreCase = true) ||
                it.biography.publisher.contains(q, ignoreCase = true)
            }
        } else {
            sampleCharacters
        }
    }

    companion object {
        val sampleCharacters = listOf(
            Character(
                id = "620",
                name = "Spider-Man",
                imageUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/md/620-spider-man.jpg",
                powerstats = Powerstats(intelligence = 90, strength = 55, speed = 67, durability = 75, power = 74, combat = 85),
                biography = Biography(
                    fullName = "Peter Parker",
                    alterEgos = "No alter egos found.",
                    aliases = listOf("Spidey", "Wall-Crawler", "Web-Slinger"),
                    placeOfBirth = "New York, New York",
                    firstAppearance = "Amazing Fantasy #15",
                    publisher = "Marvel Comics",
                    alignment = "good"
                ),
                appearance = Appearance(gender = "Male", race = "Human", height = "5'10, 178 cm", weight = "165 lb, 74 kg", eyeColor = "Hazel", hairColor = "Brown"),
                work = Work(occupation = "Photographer, Scientist", base = "New York, New York"),
                connections = Connections(groupAffiliation = "Avengers, Fantastic Four", relatives = "May Parker (aunt), Ben Parker (uncle, deceased)")
            ),
            Character(
                id = "346",
                name = "Iron Man",
                imageUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/md/346-iron-man.jpg",
                powerstats = Powerstats(intelligence = 100, strength = 85, speed = 58, durability = 85, power = 100, combat = 64),
                biography = Biography(
                    fullName = "Tony Stark",
                    alterEgos = "No alter egos found.",
                    aliases = listOf("Golden Avenger", "Armored Avenger"),
                    placeOfBirth = "Long Island, New York",
                    firstAppearance = "Tales of Suspense #39",
                    publisher = "Marvel Comics",
                    alignment = "good"
                ),
                appearance = Appearance(gender = "Male", race = "Human", height = "6'1, 185 cm", weight = "225 lb, 102 kg", eyeColor = "Blue", hairColor = "Black"),
                work = Work(occupation = "Inventor, Businessman", base = "Stark Tower, New York"),
                connections = Connections(groupAffiliation = "Avengers, Illuminati", relatives = "Howard Stark (father, deceased), Maria Stark (mother, deceased)")
            ),
            Character(
                id = "70",
                name = "Batman",
                imageUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/md/70-batman.jpg",
                powerstats = Powerstats(intelligence = 100, strength = 26, speed = 27, durability = 50, power = 47, combat = 100),
                biography = Biography(
                    fullName = "Bruce Wayne",
                    alterEgos = "No alter egos found.",
                    aliases = listOf("Dark Knight", "Caped Crusader"),
                    placeOfBirth = "Gotham City",
                    firstAppearance = "Detective Comics #27",
                    publisher = "DC Comics",
                    alignment = "good"
                ),
                appearance = Appearance(gender = "Male", race = "Human", height = "6'2, 188 cm", weight = "210 lb, 95 kg", eyeColor = "Blue", hairColor = "Black"),
                work = Work(occupation = "CEO of Wayne Enterprises", base = "Batcave, Gotham City"),
                connections = Connections(groupAffiliation = "Justice League, Batman Family", relatives = "Thomas Wayne (father, deceased), Martha Wayne (mother, deceased)")
            ),
            Character(
                id = "644",
                name = "Superman",
                imageUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/md/644-superman.jpg",
                powerstats = Powerstats(intelligence = 94, strength = 100, speed = 100, durability = 100, power = 100, combat = 85),
                biography = Biography(
                    fullName = "Clark Kent (Kal-El)",
                    alterEgos = "No alter egos found.",
                    aliases = listOf("Man of Steel", "Son of Krypton"),
                    placeOfBirth = "Krypton",
                    firstAppearance = "Action Comics #1",
                    publisher = "DC Comics",
                    alignment = "good"
                ),
                appearance = Appearance(gender = "Male", race = "Kryptonian", height = "6'3, 191 cm", weight = "235 lb, 107 kg", eyeColor = "Blue", hairColor = "Black"),
                work = Work(occupation = "Reporter for Daily Planet", base = "Metropolis, Fortress of Solitude"),
                connections = Connections(groupAffiliation = "Justice League", relatives = "Jor-El (father, deceased), Lara Lor-Van (mother, deceased)")
            ),
            Character(
                id = "149",
                name = "Captain America",
                imageUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/md/149-captain-america.jpg",
                powerstats = Powerstats(intelligence = 69, strength = 19, speed = 38, durability = 55, power = 60, combat = 100),
                biography = Biography(
                    fullName = "Steve Rogers",
                    alterEgos = "No alter egos found.",
                    aliases = listOf("Cap", "First Avenger"),
                    placeOfBirth = "Manhattan, New York",
                    firstAppearance = "Captain America Comics #1",
                    publisher = "Marvel Comics",
                    alignment = "good"
                ),
                appearance = Appearance(gender = "Male", race = "Human", height = "6'2, 188 cm", weight = "220 lb, 99 kg", eyeColor = "Blue", hairColor = "Blond"),
                work = Work(occupation = "Adventurer, Soldier", base = "New York, New York"),
                connections = Connections(groupAffiliation = "Avengers, Invaders", relatives = "Joseph Rogers (father, deceased)")
            ),
            Character(
                id = "659",
                name = "Thor",
                imageUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/md/659-thor.jpg",
                powerstats = Powerstats(intelligence = 69, strength = 100, speed = 83, durability = 100, power = 100, combat = 100),
                biography = Biography(
                    fullName = "Thor Odinson",
                    alterEgos = "No alter egos found.",
                    aliases = listOf("God of Thunder", "Son of Odin"),
                    placeOfBirth = "Asgard",
                    firstAppearance = "Journey into Mystery #83",
                    publisher = "Marvel Comics",
                    alignment = "good"
                ),
                appearance = Appearance(gender = "Male", race = "Asgardian", height = "6'6, 198 cm", weight = "640 lb, 288 kg", eyeColor = "Blue", hairColor = "Blond"),
                work = Work(occupation = "King of Asgard, Avenger", base = "Asgard, New York"),
                connections = Connections(groupAffiliation = "Avengers, Gods of Asgard", relatives = "Odin (father), Frigga (mother), Loki (adopted brother)")
            )
        )
    }
}
