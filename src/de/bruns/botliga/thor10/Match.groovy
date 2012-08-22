package de.bruns.botliga.thor10

class Match {
   
  int matchId
  int matchday
  int homeTeamId
  int guestTeamId
  int homeTeamGoals 
  int guestTeamGoals 
   
  Match(matchId, matchday, homeTeamId, guestTeamId, homeTeamGoals, guestTeamGoals) {
    this.matchId = matchId
    this.matchday = matchday
    this.homeTeamId = homeTeamId
    this.guestTeamId = guestTeamId
    this.homeTeamGoals = homeTeamGoals
    this.guestTeamGoals = guestTeamGoals
  }

  Match(matchId, matchday, homeTeamId, guestTeamId) {
     this.matchId = matchId
     this.matchday = matchday
     this.homeTeamId = homeTeamId
     this.guestTeamId = guestTeamId
  }
  
  def setResult(homeTeamGoals, guestTeamGoals) {
    this.homeTeamGoals = homeTeamGoals
    this.guestTeamGoals = guestTeamGoals
  }
}
