/**
 * All in one file
 * Created by Gvasko on 2015.04.24..
 */

def weather =
"""\
  Dy MxT   MnT   AvT   HDDay  AvDP 1HrP TPcpn WxType PDir AvSp Dir MxS SkyC MxR MnR AvSLP

   1  88    59    74          53.8       0.00 F       280  9.6 270  17  1.6  93 23 1004.5
   2  79    63    71          46.5       0.00         330  8.7 340  23  3.3  70 28 1004.5
   3  77    55    66          39.6       0.00         350  5.0 350   9  2.8  59 24 1016.8
   4  77    59    68          51.1       0.00         110  9.1 130  12  8.6  62 40 1021.1
   5  90    66    78          68.3       0.00 TFH     220  8.3 260  12  6.9  84 55 1014.4
   6  81    61    71          63.7       0.00 RFH     030  6.2 030  13  9.7  93 60 1012.7
   7  73    57    65          53.0       0.00 RF      050  9.5 050  17  5.3  90 48 1021.8
   8  75    54    65          50.0       0.00 FH      160  4.2 150  10  2.6  93 41 1026.3
   9  86    32*   59       6  61.5       0.00         240  7.6 220  12  6.0  78 46 1018.6
  10  84    64    74          57.5       0.00 F       210  6.6 050   9  3.4  84 40 1019.0
  11  91    59    75          66.3       0.00 H       250  7.1 230  12  2.5  93 45 1012.6
  12  88    73    81          68.7       0.00 RTH     250  8.1 270  21  7.9  94 51 1007.0
  13  70    59    65          55.0       0.00 H       150  3.0 150   8 10.0  83 59 1012.6
  14  61    59    60       5  55.9       0.00 RF      060  6.7 080   9 10.0  93 87 1008.6
  15  64    55    60       5  54.9       0.00 F       040  4.3 200   7  9.6  96 70 1006.1
  16  79    59    69          56.7       0.00 F       250  7.6 240  21  7.8  87 44 1007.0
  17  81    57    69          51.7       0.00 T       260  9.1 270  29* 5.2  90 34 1012.5
  18  82    52    67          52.6       0.00         230  4.0 190  12  5.0  93 34 1021.3
  19  81    61    71          58.9       0.00 H       250  5.2 230  12  5.3  87 44 1028.5
  20  84    57    71          58.9       0.00 FH      150  6.3 160  13  3.6  90 43 1032.5
  21  86    59    73          57.7       0.00 F       240  6.1 250  12  1.0  87 35 1030.7
  22  90    64    77          61.1       0.00 H       250  6.4 230   9  0.2  78 38 1026.4
  23  90    68    79          63.1       0.00 H       240  8.3 230  12  0.2  68 42 1021.3
  24  90    77    84          67.5       0.00 H       350  8.5 010  14  6.9  74 48 1018.2
  25  90    72    81          61.3       0.00         190  4.9 230   9  5.6  81 29 1019.6
  26  97*   64    81          70.4       0.00 H       050  5.1 200  12  4.0 107 45 1014.9
  27  91    72    82          69.7       0.00 RTH     250 12.1 230  17  7.1  90 47 1009.0
  28  84    68    76          65.6       0.00 RTFH    280  7.6 340  16  7.0 100 51 1011.0
  29  88    66    77          59.7       0.00         040  5.4 020   9  5.3  84 33 1020.6
  30  90    45    68          63.6       0.00 H       240  6.0 220  17  4.8 200 41 1022.7
  mo  82.9  60.5  71.7    16  58.8       0.00              6.9          5.3
"""

def football =
"""\
       Team            P     W    L   D    F      A     Pts
    1. Arsenal         38    26   9   3    79  -  36    87
    2. Liverpool       38    24   8   6    67  -  30    80
    3. Manchester_U    38    24   5   9    87  -  45    77
    4. Newcastle       38    21   8   9    74  -  52    71
    5. Leeds           38    18  12   8    53  -  37    66
    6. Chelsea         38    17  13   8    66  -  38    64
    7. West_Ham        38    15   8  15    48  -  57    53
    8. Aston_Villa     38    12  14  12    46  -  47    50
    9. Tottenham       38    14   8  16    49  -  53    50
   10. Blackburn       38    12  10  16    55  -  51    46
   11. Southampton     38    12   9  17    46  -  54    45
   12. Middlesbrough   38    12   9  17    35  -  47    45
   13. Fulham          38    10  14  14    36  -  44    44
   14. Charlton        38    10  14  14    38  -  49    44
   15. Everton         38    11  10  17    45  -  57    43
   16. Bolton          38     9  13  16    44  -  62    40
   17. Sunderland      38    10  10  18    29  -  51    40
   -------------------------------------------------------
   18. Ipswich         38     9   9  20    41  -  64    36
   19. Derby           38     8   6  24    33  -  63    30
   20. Leicester       38     5  13  20    30  -  64    28
"""

class LineParser {
    String line
    int beginIndex = 0
    int endIndex = 0

    LineParser(String line) {
        this.line = line
    }

    String getNextField(int width) {
        endIndex = Math.min(beginIndex + width, line.length())
        String ret = line[beginIndex..endIndex-1].replace('*', ' ').trim()
        beginIndex = endIndex
        return ret
    }
}

class TableParser {
    String data
    List<Integer> widths
    List<String> header = []
    LineParser lineP

    TableParser(String data, List widths) {
        this.data = data
        this.widths = widths
    }

    List asListOfRecords() {
        List table = []
        def processHeader = true
        data.eachLine { line ->
            if (line.trim()) {
                lineP = new LineParser(line)
                if (processHeader) {
                    header = readHeader()
                    processHeader = false
                } else {
                    table << readRecord()
                }
            }
        }
        table
    }

    private readHeader() {
        List header = []
        widths.forEach { int width ->
            header << lineP.getNextField(width)
        }
        header
    }

    private readRecord() {
        assert header != null && header.size() > 0
        def record = [:]
        [header, widths].transpose().forEach { hw ->
            String field = hw[0]
            int width = hw[1]
            record[field] = lineP.getNextField(width)
        }
        record
    }

}

private float getDiff(Map rec, String field1, String field2) {
    Math.abs(Float.parseFloat(rec[field1].toString()) - Float.parseFloat(rec[field2].toString()))
}

private Map getMinDiffRecord(List dayMinMax, String field1, String field2) {
    dayMinMax.min { rec1, rec2 ->
        Float.compare(getDiff(rec1, field1, field2), getDiff(rec2, field1, field2))
        }
}

weatherTableParser = new TableParser(weather, [5, 6, 6])
weatherTable = weatherTableParser.asListOfRecords()
weatherTable = weatherTable.take(weatherTable.size() - 1)
def minDay = getMinDiffRecord(weatherTable, "MxT", "MnT")
println minDay["Dy"]

footballTableParser = new TableParser(football, [7, 16, 6, 4, 4, 6, 4, 3, 6, 3])
footballTable = footballTableParser.asListOfRecords()
footballTable = footballTable.findAll { !it.Team.startsWith("----") }
def minTeam = getMinDiffRecord(footballTable, "F", "A")
println minTeam["Team"]
