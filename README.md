Summary
=======
Explore producing streams using Java.

Overview
--------
The original inspiration for this project was to create a library that could fill in for 
a "real" stream.

Goals
-----
* use of protocol-buffer like messaging
* generate streams of protocol-buffer like messages
* serialize protocol-buffer messages streams to disk
* load serialized stream from disk
* use java stream api's to work with streams


What is a "real" stream?
------------------------
In this project, a real stream is one produced from a real-world system.  A real-world system
is represented by the state it maintains, the behavior it exhibits, the events it responds to,
and most relevant here, the events it produces.

Absent a real-world system, how can one produce a "life-like" stream absent a real-world system?

"Realistic streams"
-------------------
Just what is a realistic stream?

There could be several answers depending on the intended usage.

Here are a few, roughly ranging from least realistic to most:

* Contains all the message types a consumer expects, each message doesn't need to contain valid data, or even changing data.

  In other words, just enough to drive a consumer.

* Each message should contain valid values.
* The sequence of messages should be realistic.
* The difference in values across messages should be realistic.
* Message patterns typical of some underlying event should be present.


What is a stream?
-----------------
In the context under discussion, a stream represents some aspect of the system generating
the stream.  In other words, a stream is a projection of an underlying system.  By consuming
a stream, one can get a sense of what is happening in the underlying system.  A stream consists
of so-called sequenced events. 

Sequenced events
----------------
A sequenced event is something of interest that has been published to a stream.  


What causes an event to be sequenced to a stream?
-------------------------------------------------
* The underlying system wants to publish reference data of some kind for downstream consumers.
* The some event of consequence has occurred on the underlying system that consumers will want to know about.
* Events of importance to fully understanding other events are published.
* State transitions on the underlying system occur, and are published to the event stream.


 
Generating a stream
-------------------
Theoretically, a stream could be generated in one of several ways:
 * replayed from a previously archived stream
 * staged in some kind of file or in memory representation, carefully scripted to be representative
   of a real stream.
 * dynamically generated according to some kind of algorithm, state machine or otherwise.


Aspects of an underlying system
-------------------------------
What reference data does it use?
What events of importance occur and are of interest to a client?
What causes these events to occur?
What data is required to fully understand these events?
What state governs the occurence of and values associated with given events?


Producing a realistic stream "from scratch"
-------------------------------------------
It is reasonable to assume it is impossible to create a 100% realistic stream in many cases, most
obviously in the case of complex underlying systems.  

The following need to be considered:
 * What message types does the stream consist of?
 * What is the structure of each message?
 * When event(s) in the underlying system does each message represent.
 * Does the stream require reference data in general be published?
 * For each message, what supporting messages are required to provide
   context for the client.
 * What state in the underlying system drives the values present in the stream messages?
 * How can an accurate sequence of messages be generated absent a real system?
 * In what order do messages typically get emitted.


Randomization and Determinism
-----------------------------
There is typically an internal logic to streams emitted from a trading system, for example, or 
related real-time system.  This is a result of the the system on which the stream is based.
Field values across consecutive messages may only make sense if maintained within a certain 
range, as opposed to fluctuating wildly and randomly.  This is probably based on some state
on which a realistic stream would be based.  

Randomization may be based on some underlying level of determinism.  During an exchange opening
process, the number of options open starts at 0 and increases rapidly to some percentage of
total listed options close to 100%, with some slight jitter as options open or close for 
some reason.  At close, the number of options closed will immediately equal 100%, with the exception
of those products that close at another time.

Certain events can be known to occur, for example, the opening and closing event in an exchange.
Other events

