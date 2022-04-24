
startingPort=5000
for i in $(seq 5)
do
	j=$((i + startingPort));
	echo "Kill Vital Monitor: CICU_$i at port: $j"
	# kill vital monitor
    kill $(ps -ef | grep "CICU_$i" | grep -v grep | awk '{print $2}')
done
