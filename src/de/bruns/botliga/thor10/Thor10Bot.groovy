package de.bruns.botliga.thor10

@Grapes([
   @Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.2' ),
   @Grab(group='net.sf.json-lib', module='json-lib', version='2.4', classifier='jdk15'),
   @Grab(group='commons-beanutils', module='commons-beanutils', version='1.8.3')]
)
                   
import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

statisticRepository = new StatisticRepository() 

def loadRound(matchday, year) {
   
      def http = new HTTPBuilder( 'http://openligadb-json.heroku.com' )
      
      // perform a GET request, expecting JSON response data
      http.request( GET, JSON ) {
        uri.path = '/api/matchdata_by_group_league_saison'
        uri.query = [ group_order_id: matchday, league_saison: year, league_shortcut: 'bl1' ]
      
        headers.'User-Agent' = 'Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4'
      
        // response handler for a success response code:
        response.success = { resp, json ->
          println json.matchdata[0].group_name + " -> " + json.matchdata[0].match_date_time
      
          // parse the JSON response object:
          json.matchdata.each {
             println "  ${it.name_team1} - ${it.points_team1} : ${it.points_team2} - ${it.name_team2}"
             if (it.points_team1.toInteger() >= 0 && it.points_team2.toInteger() >=0) {
                statisticRepository.addMatch(new Match(0, matchday, it.id_team1.toInteger(), it.id_team2.toInteger(), it.points_team1.toInteger(), it.points_team2.toInteger()))
             }
          }
        }

        // handler for any failure status code:
        response.failure = { resp ->
          println "Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}"
        }
      } 
}  

def teams = [
   100 : "Hamburger SV",
   9 : "FC Schalke 04",
   7 : "Borussia Dortmund",
   6 : "Bayer Leverkusen",
   40 : "FC Bayern München",
   131 : "VfL Wolfsburg",
   16 : "VfB Stuttgart",
   134 : "Werder Bremen",
   87 : "Bor. Mönchengladbach",
   55 : "Hannover 96",
   112 : "SC Freiburg",
   95 : "FC Augsburg",
   91 : "Eintracht Frankfurt",
   185 : "Fortuna Düsseldorf",
   79 : "1. FC Nürnberg",
   81 : "1. FSV Mainz 05",
   115 : "SpVgg Greuther Fürth",
   123 : "TSG 1899 Hoffenheim",
   ]

int matchday = 4
int matchyear = 2012
int numberOfMatchdaysToAnalyze = 10

def currentMatchdayService = new CurrentMatchdayService()
def currentMatches = currentMatchdayService.matches(matchday, matchyear)

for (i = numberOfMatchdaysToAnalyze; i > 0; i--) {
   def localMatchyear = matchyear
   def localMatchday = matchday - i
   if (localMatchday <= 0) {
      localMatchday = localMatchday + 34
      localMatchyear = localMatchyear - 1
   }
   loadRound(localMatchday, localMatchyear)
}

statisticRepository.calculateStatistics(teams)

currentMatches.each {
   statisticRepository.guessMatch(it)
}

println "------------- Guessed Matches -------------------"

def httpPostScore = new HTTPBuilder('http://botliga.de/')
currentMatches.each {
   println  it.matchId + ": " + it.homeTeamId + " - " + it.guestTeamId + " => " + it.homeTeamGoals + " : " + it.guestTeamGoals + "   (" + teams[it.homeTeamId] + "  -  " + teams[it.guestTeamId] + ")"

   def postBody = [ match_id : it.matchId , result : (it.homeTeamGoals +":" + it.guestTeamGoals), token : 'qbhnft06s54jqiueebulop1j' ] // will be url-encoded
   httpPostScore.post( path: '/api/guess', body: postBody, requestContentType: URLENC ) { resp ->
      println "Match response status: ${resp.statusLine}"
   }
}


