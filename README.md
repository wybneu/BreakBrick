# BreakBrick
This is a J2ME BreakBrick Game with function internet rank, level updata, different setting and mobile relocated

A Cooperative Breakout Game
The goal of the game is to remove all bricks with a ball that is hit by the player.
A brick is removed once it has been hit by the player’s ball. 
The ball bounces off the removed brick. 
The player’s ball is controlled by a paddle. 
The ball bounces from all edges but the lower edge. 
If the ball hits the lower edge of the playing area, a life is lost. 
A player has up to three lives. 
If all bricks are removed from the player’s screen, the game advances to the next level. 
If the player has no more lives left, the game is over. 
If the high score is in the current top 10, the game asks the player, if an entry to the high score list is desired. 
After each played game, the player should see the welcome screen (detailed below)

Some bricks have extra bonuses: a brick could split the ball into two or three balls, reduce or enlarge the player’s paddle, or provide an extra life.
The game level file can only use relative measures to facilitate portability. Each brick is specified in relative sizes, i.e., x % of the screen width and y % of the screen height. The game level file has to include the speed, direction, and initial position of the ball, and the size of the paddle. You can assume that the paddle is at rest and horizontally centered. Once several teams have implemented their version of the game file, we will take the best one as a reference and all other programs should be able read in this reference game file.
If the ball hits a paddle at rest it just reverses the direction of the ball in x- and y-direction.
However, if the player moves the paddle, the horizontal speed of the paddle is added to the horizontal component of the ball’s speed.
Extra challenge

Up to now the game can only be played by a single player. Future task is to design a collaborative version of this game where another player’s paddle is located at the opposite of the screen. The game updates between the two players are performed via a Bluetooth connection. This task is difficult since it requires a permanent synchronization between the two players. If you like to take the collaborative game a step further, even 4 players could be connected for one game, i.e., at each side of the game is a paddle of each participating player. The team loses the game until either one of the players has no live left or, if there is a common number of lives for all players, no live for the team is left.

#How to install
1 The BreakBrickServer file's need to load by a tomcat to run a server to keep the top 10 informtion and the download levels
2 The BreakBrick files need to copy to Mobile support J2ME 
3 All the files can be load to the eclipse J2ME 

#Copyright
There is no copyright cause this project is only my project when study. Anyone can edit, use but need to reference it. 

Thanks you for any ideas and help.
