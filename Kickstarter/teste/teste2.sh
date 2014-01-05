cd ~/Desktop/GitHub/SD/Kickstarter/teste

for i in `seq 1 100`
do
echo "1" >> $i.input
echo $i >> $i.input
echo $i >> $i.input
echo "2" >> $i.input
echo $i >> $i.input
echo $i >> $i.input
echo "1" >> $i.input
echo $i >> $i.input
echo $i >> $i.input
echo "50" >> $i.input
echo "6" >> $i.input
echo "3" >> $i.input
done


for i in `seq 101 200`
do
	echo "1" >> $i.input
	echo $i >> $i.input
	echo $i >> $i.input
	echo "2" >> $i.input
	echo $i >> $i.input
	echo $i >> $i.input
	echo "2" >> $i.input
	echo $(( i-100 )) >> $i.input
	echo "10" >> $i.input
	echo "6" >> $i.input
	echo "3" >> $i.input
done

cd ~/Desktop/GitHub/SD/Kickstarter/build/classes

for i in `seq 1 100`
do
java kickstarter.ClienteKickstarter < ../../teste/$i.input > /dev/null &
done

sleep 20

echo "a financiar projectos"

for i in `seq 101 200`
do
	java kickstarter.ClienteKickstarter < ../../teste/$i.input > /dev/null &
done