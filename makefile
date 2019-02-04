# launch monitoring in-place (no-jar)
gbootrun:
	gradle bootRun

gclean:
	gradle clean

# create executable jar
gbootrepackage:
	gradle bootRepackage

gcreaterpm:
	gradle bootRepackage createRpm

stagenewrpm:
	cp build/distributions/*.rpm docker/

run:
	java -jar ./build/libs/iris-app-0.0.1.jar
