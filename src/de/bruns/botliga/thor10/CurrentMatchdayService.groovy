package de.bruns.botliga.thor10

@Grapes([
   @Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.2' ),
   @Grab(group='net.sf.json-lib', module='json-lib', version='2.4', classifier='jdk15'),
   @Grab(group='commons-beanutils', module='commons-beanutils', version='1.8.3')]
)

import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

class CurrentMatchdayService {

   def matches(round, year) {
      def allMatches = []

      def http = new HTTPBuilder( 'http://openligadb-json.heroku.com' )
      
      // perform a GET request, expecting JSON response data
      http.request( GET, JSON ) {
        uri.path = '/api/matchdata_by_group_league_saison'
        uri.query = [ group_order_id: round, league_saison: year, league_shortcut: 'bl1' ]
      
        headers.'User-Agent' = 'Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4'
      
        // response handler for a success response code:
        response.success = { resp, json ->
          // println json.matchdata[0].group_name + " -> " + json.matchdata[0].match_date_time
      
          // parse the JSON response object:
          json.matchdata.each {
            allMatches.add(new Match(it.match_id.toInteger(), round, it.id_team1.toInteger(), it.id_team2.toInteger()))
            // println "  ${it.name_team1} - ${it.points_team1} : ${it.points_team2} - ${it.name_team2}"
          }
        }
      
        // handler for any failure status code:
        response.failure = { resp ->
          println "Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}"
        }
      }
      
      allMatches
   }

}
