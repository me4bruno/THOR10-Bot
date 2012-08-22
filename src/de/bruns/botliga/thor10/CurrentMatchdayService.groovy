package de.bruns.botliga.thor10

class CurrentMatchdayService {

   def matches(round, year) {
      def allMatches = []
      if (round == 1) {
         allMatches.add(new Match(19702, 1, 7, 134))
         allMatches.add(new Match(19703, 1, 87, 123))
         allMatches.add(new Match(19706, 1, 112, 81))
         allMatches.add(new Match(19707, 1, 95, 185))
         allMatches.add(new Match(19708, 1, 100, 79))
         allMatches.add(new Match(19709, 1, 115, 40))
         allMatches.add(new Match(19710, 1, 91, 6))
         allMatches.add(new Match(19704, 1, 16, 131))
         allMatches.add(new Match(19705, 1, 55, 9))
      } else if (round == 2) {
         allMatches.add(new Match(19718, 2, 81, 115))
         allMatches.add(new Match(19712, 2, 9, 95))
         allMatches.add(new Match(19713, 2, 6, 112))
         allMatches.add(new Match(19715, 2, 134, 100))
         allMatches.add(new Match(19716, 2, 79, 7))
         allMatches.add(new Match(19717, 2, 123, 91))
         allMatches.add(new Match(19719, 2, 185, 87))
         allMatches.add(new Match(19714, 2, 131, 55))
         allMatches.add(new Match(19711, 2, 40, 16))
      } else if (round == 3) {
         allMatches.add(new Match(19726, 3, 95, 131))
         allMatches.add(new Match(19720, 3, 7, 6))
         allMatches.add(new Match(19721, 3, 40, 81))
         allMatches.add(new Match(19722, 3, 87, 79))
         allMatches.add(new Match(19723, 3, 16, 185))
         allMatches.add(new Match(19724, 3, 55, 134))
         allMatches.add(new Match(19727, 3, 115, 9))
         allMatches.add(new Match(19725, 3, 112, 123))
         allMatches.add(new Match(19728, 3, 91, 100))
      }
      allMatches
   }
}
