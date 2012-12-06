1) Eclipse Project (ObjectSerDeser_00001.rar) with an Android 
application that does object serialization/deserialization via 
AsyncTask. 

The user can start a 2D Tic-Tac-Toe frame animation by selecting 
"Animate" on the options menu. As the animation is playing on 
the main UI thread, the user can click on "Serialize" to spawn 
a serialization AsyncTask off the main UI thread. When the serialization 
task finishes with a Toast, the user can select "Deserialize" to 
spawn a deserialization AsyncTask off the main UI thread.
 
The serialization AsyncTask (TTTGameStateTreeSerializer.java) inflates two 
TreeMap<String, BoardUtility> objects from assets/large_game_state_tree.txt 
and assets/small_game_tree.txt and then serializes both TreeMap<String, BoardUtility> 
objects onto the sdcard. Both text files encode Tic-Tac-Toe board utility trees.
The file small_game_state_tree.txt encodes a small incomplete tree.  The
file large_game_state_tree.txt encodes a game tree that contains 5478 states.
Each line has the following format: x???o????;X;1:0 2:0 3:0 5:0 6:0 7:0 8:0.
 
"x???0????" encodes the following board state ('?' stands for 'empty'):

-------------
| x | ? | ? |
-------------
| ? | o | ? |
-------------
| ? | ? | ? |
-------------

'X' following the board state encoding denotes the player whose turn it is to
play on this board state. The string "1:0 2:0 3:0 5:0 6:0 7:0 8:0" encodes
the move positions available to each X and their utilities (there are three 
possible utilities: -1 (loss for X), 0 (draw), 1 (win for X)).

The deserializaton AsyncTask (TTTGameStateTreeDeserializer.java) two TreeMap<String, BoardUtility> 
objects from sdcard files off the main UI thread. When an object is deserialized a Toast 
is shown on the main UI thread.

2) Targets: Android 2.3+, Eclipse Helios 3.6

3) To start: 
   a) unzip into a folder 
   b) open in Eclipse 
   c) Run as Android application
