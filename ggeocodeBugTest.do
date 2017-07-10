// Test/Example cases for ggeocode
input int housenum str13 street str10 city str2 state str5 zip
4287 "46th Ave N" "Robbinsdale" "MN" "55422"
6675 "Old Canton Rd" "Ridgeland" "MS" "39157"
12313 "33rd Ave NE" "Seattle" "WA" "98125"
310 "Cahir St" "Providence" "RI" "02903"
22 "Oaklawn Ave" "Cranston" "RI" "02920"
61 "Pine St" "Attleboro" "MA" "02703"
10 "Larkspur Rd" "Warwick" "RI" "02886"
91 "Fallon Ave" "Providence" "RI" "02908"
195 "Arlington Ave" "Providence" "RI" "02906"
end
ggeocode housenum street city state zip if state == "RI"
ggeocode housenum street city state zip if state != "RI", ret(bbox location geotype address location)
