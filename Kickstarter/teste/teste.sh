cd ~/Desktop/GitHub/SD/Kickstarter/teste

i=0;
echo "1" >> $i.input
echo $i >> $i.input
echo $i >> $i.input
echo "2" >> $i.input
echo $i >> $i.input
echo $i >> $i.input
echo "1" >> $i.input
echo "ProjectoTESTE!!!!!" >> $i.input
echo "sssssssssssssssss" >> $i.input
echo "10000" >> $i.input
echo "6" >> $i.input
echo "3" >> $i.input


for i in `seq 1 150`
do
	echo "1" >> $i.input
	echo $i >> $i.input
	echo $i >> $i.input
	echo "2" >> $i.input
	echo $i >> $i.input
	echo $i >> $i.input
	echo "2" >> $i.input
	echo "1" >> $i.input
	echo "1" >> $i.input
	echo "6" >> $i.input
	echo "3" >> $i.input
done

cd ~/Desktop/GitHub/SD/Kickstarter/build/classes

java kickstarter.ClienteKickstarter < ../../teste/0.input > ../../teste/0.output &

sleep 5


for i in `seq 1 150`
do
	java kickstarter.ClienteKickstarter < ../../teste/$i.input > /dev/null &
done




