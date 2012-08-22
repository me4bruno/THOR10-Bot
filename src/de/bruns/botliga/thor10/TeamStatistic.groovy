package de.bruns.botliga.thor10

class TeamStatistic {

  def teamId, ownGoalsAsHomeTeam, opponentGoalsAsHomeTeam, ownGoalsAsGuestTeam, opponentGoalsAsGuestTeam
   
  TeamStatistic(teamId) {
    this.teamId = teamId
    this.ownGoalsAsHomeTeam = []
    this.opponentGoalsAsHomeTeam = []
    this.ownGoalsAsGuestTeam = []
    this.opponentGoalsAsGuestTeam  = []
  }
  
  def ownGoalsAsHomeTeamAverage() {
    if (ownGoalsAsHomeTeam.size() == 0) {
       ownGoalsAsHomeTeam.add(0)
    }
    return findAverage(ownGoalsAsHomeTeam)
  }

  def opponentGoalsAsHomeTeamAverage() {
     if (opponentGoalsAsHomeTeam.size() == 0) {
        opponentGoalsAsHomeTeam.add(4)
     }
     return findAverage(opponentGoalsAsHomeTeam)
  }

  def ownGoalsAsGuestTeamAverage() {
     if (ownGoalsAsGuestTeam.size() == 0) {
        ownGoalsAsGuestTeam.add(0)
     }
     return findAverage(ownGoalsAsGuestTeam)
  }
  
  def opponentGoalsAsGuestTeamAverage() {
     if (opponentGoalsAsGuestTeam.size() == 0) {
        opponentGoalsAsGuestTeam.add(4)
     }
     return findAverage(opponentGoalsAsGuestTeam)
  }

  def findAverage(list) {
      return list.sum() / list.size()
  }

}
