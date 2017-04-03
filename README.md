# name-of-thrones

Converts normal names into name that are worthy of a run at the iron throne.

## Overview

This project takes a first and last name, and concatenates it with a randomly selected Game of Thrones name based on some trickery. It is a silly string manipulation clojurescript project that I created in a coule hours in my free time. Open to suggestions for how to make the naming chopper and adder stuff better.

## Setup

To get an interactive development environment run:

    lein figwheel

and open your browser at [localhost:3449](http://localhost:3449/).
This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To clean all compiled files:

    lein clean

To create a production build run:

    lein do clean, cljsbuild once min

And open your browser in `resources/public/index.html`. You will not
get live reloading, nor a REPL. 

## License

Copyright © 2017 Peter DePaulo

Distributed under the IDGAF Public License.