package de.bruns.botliga.thor10

class StatisticRepository {

   def list, teamStatistics
   def ownGoalsAsHomeTeamSortedList, opponentGoalsAsHomeTeamSortedList, ownGoalsAsGuestTeamSortedList, opponentGoalsAsGuestTeamSortedList
   
   StatisticRepository() {
      list = []
      teamStatistics = [:]
   }
   
   def addMatch(match) {
      list.add(match)
   }

   def calculateStatistics(teams) {
      list.each {
         if (!teamStatistics.containsKey(it.homeTeamId))
            teamStatistics[it.homeTeamId] = new TeamStatistic(it.homeTeamId)
         def homeStatistic = teamStatistics[it.homeTeamId]
         homeStatistic.ownGoalsAsHomeTeam.add(it.homeTeamGoals)
         homeStatistic.opponentGoalsAsHomeTeam.add(it.guestTeamGoals)
               
         if (!teamStatistics.containsKey(it.guestTeamId))
            teamStatistics[it.guestTeamId] = new TeamStatistic(it.guestTeamId)
         def guestStatistic = teamStatistics[it.guestTeamId]
         guestStatistic.ownGoalsAsGuestTeam.add(it.guestTeamGoals)
         guestStatistic.opponentGoalsAsGuestTeam.add(it.homeTeamGoals)
      }

      def teamsToRemove = []
      teamStatistics.each {
         if (! teams.containsKey(it.key)) {
            teamsToRemove.add(it.key)
         }
      }
      
      teamsToRemove.each {
         teamStatistics.remove(it)
      }
      
      teams.each {
         if (!teamStatistics.containsKey(it.key)) {
            teamStatistics[it.key] = new TeamStatistic(it.key)
            def newTeamStatistic = teamStatistics[it.key]
            // unknown teams get 0:4 matches as home and as guest team
            newTeamStatistic.ownGoalsAsHomeTeam.add(0)
            newTeamStatistic.opponentGoalsAsHomeTeam.add(4)
            newTeamStatistic.ownGoalsAsGuestTeam.add(0)
            newTeamStatistic.opponentGoalsAsGuestTeam.add(4)
         }
      }
      
      ownGoalsAsHomeTeamSortedList = teamStatistics.sort {a, b -> b.value.ownGoalsAsHomeTeamAverage() <=> a.value.ownGoalsAsHomeTeamAverage()}
      opponentGoalsAsHomeTeamSortedList = teamStatistics.sort {a, b -> a.value.opponentGoalsAsHomeTeamAverage() <=> b.value.opponentGoalsAsHomeTeamAverage()}
      ownGoalsAsGuestTeamSortedList = teamStatistics.sort {a, b ->  b.value.ownGoalsAsGuestTeamAverage() <=> a.value.ownGoalsAsGuestTeamAverage()}
      opponentGoalsAsGuestTeamSortedList = teamStatistics.sort {a, b -> a.value.opponentGoalsAsGuestTeamAverage() <=> b.value.opponentGoalsAsGuestTeamAverage()}
   }
   
   def getOwnGoalsAsHomeTeamRanking(teamId) {
      return indexOfObject(ownGoalsAsHomeTeamSortedList, teamStatistics[teamId])
   }
   
   def getOpponentGoalsAsHomeTeamRanking(teamId) {
      return indexOfObject(opponentGoalsAsHomeTeamSortedList, teamStatistics[teamId])
   }
   def getOwnGoalsAsGuestTeamRanking(teamId) {
      return indexOfObject(ownGoalsAsGuestTeamSortedList, teamStatistics[teamId])
   }
   
   def getOpponentGoalsAsGuestTeamRanking(teamId) {
      return indexOfObject(opponentGoalsAsGuestTeamSortedList, teamStatistics[teamId])
   }
   
   def guessMatch(match) {
      def homeTeamId = match.homeTeamId
      def guestTeamId = match.guestTeamId
      
      def ownGoalsAsHomeTeamRanking = getOwnGoalsAsHomeTeamRanking(homeTeamId) + 1
      def opponentGoalsAsGuestTeamRanking = getOpponentGoalsAsGuestTeamRanking(guestTeamId) + 1
      def homeTeamGoalsRanking = bonus(ownGoalsAsHomeTeamRanking) - bonus(opponentGoalsAsGuestTeamRanking)
      
      def ownGoalsAsGuestTeamRanking = getOwnGoalsAsGuestTeamRanking(guestTeamId) + 1
      def opponentGoalsAsHomeTeamRanking = getOpponentGoalsAsHomeTeamRanking(homeTeamId) + 1
      def guestTeamGoalsRanking = bonus(ownGoalsAsGuestTeamRanking) - bonus(opponentGoalsAsHomeTeamRanking)
      
      def homeScore = 1.75 + homeTeamGoalsRanking
      def guestScore = 1.25 + guestTeamGoalsRanking

      // println homeTeamId + " - " + guestTeamId + ": " + homeTeamGoalsRanking + " / " + guestTeamGoalsRanking + " => " + homeScore + " / " + guestScore + " => " + Math.round(homeScore) + "/" + Math.round(guestScore)
      match.setResult(Math.round(homeScore), Math.round(guestScore))
   }
   
   def indexOfObject(map, object) {
      int returnValue = 0
      int i = 0
      map.each {
         if (it.value.equals(object)) {
             returnValue = i
         }
         i = i + 1
      }
      return returnValue
   }

   def bonus(tableRank) {
     if ((1..3).contains(tableRank))
        return 1;
     if ((4..7).contains(tableRank))
        return 0.5;
     if ((12..15).contains(tableRank))
        return -0.5;
     if ((16..18).contains(tableRank))
        return -1;
     return 0
   }

}
