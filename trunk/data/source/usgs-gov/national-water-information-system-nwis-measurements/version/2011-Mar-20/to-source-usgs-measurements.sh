#!/bin/bash

#Usage:
#You need to execute collect-county-code.sh
# We usually crawl data from USGS once a state. 
# To do so, you need to change the constant at line 58!!!
# Enf of Usage--------------------------------
#for test
#FILES=/home/ping/research2009-2010/python/water2/test/countyCodes/*
#OutputDir=/home/ping/research2009-2010/python/water2/test/sites
#OutputDir=/home/ping/research2009-2010/python/water2/test/results

#for real use
FILES=manual/county-code-txt/*
OutputDir=source
#echo $FILES
#echo $OutputDir

#WebTarget="http://qwwebservices.usgs.gov/Station/search?statecode="
#PostFix=-sites
WebTarget="http://qwwebservices.usgs.gov/Result/search?statecode="
PostFix=-result

crawl_one_state()
{
  statecode="US:"$1
  echo $statecode

  #iterate through the county codes in the file
  while read line
  do
  echo $line
  fname=${line//:/-}$PostFix
  echo $fname

  if [ -f $OutputDir"/"$sCode"/"$fname".zip" ]
  then
    echo "$OutputDir/$sCode/$fname.zip exists"
  else
    pcurl.sh $WebTarget$statecode"&countycode="$line"&mimeType=csv&zip=yes" -n $OutputDir/$sCode/$fname -e "zip"
    #sleep 1
  fi

  done < $2
}

 #iterate through the files for the states
for f in $FILES
do
  # take action on each file. $f store current file name
  #echo "Processing $f"  
  name=$(basename "$f")
  # skip the provenance files
  if [[ $name != *.pml.ttl ]]; then
	 echo "Processing $name" 
     sCode=${name:3:2}
     #only retrieve data for 36(NY), 06(CA), 44(RI), 25(MA)
     if [ $sCode == "25" ]; then	
        if [ ! -d $OutputDir/$sCode ]; then
           mkdir $OutputDir/$sCode
        fi
        #download the csv files for the water sites in one state
        #cd $OutputDir/$sCode
        crawl_one_state $sCode $f  
     fi
  fi #end of skip the provenance files
done

