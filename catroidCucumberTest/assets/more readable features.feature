# Catroid: An on-device visual programming system for Android devices
# Copyright (C) 2010-2014 The Catrobat Team
# (<http://developer.catrobat.org/credits>)
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as
# published by the Free Software Foundation, either version 3 of the
# License, or (at your option) any later version.
#
# An additional term exception under section 7 of the GNU Affero
# General Public License, version 3, is available at
# http://developer.catrobat.org/license_additional_term
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
Feature: Broadcast & Wait Blocking Behavior (like in Scratch)

  If a broadcast is sent while a Broadcast Wait brick is waiting for the same message, the
  responding When scripts should be restarted and the Broadcast Wait brick should stop waiting
  and immediately continue executing the rest of the script.

  Background:
    I have a Program
    and this program has an Object 'test object'

  Scenario: A BroadcastAndWait brick without a corresponding WhenBroadcastReceived script should *not* wait for anything.
    Object "test object" has the following scripts
      when program started
        broadcast "This message does not matter as there is no receiver script" and wait
        print "a"
      when program started
        wait 0.2 seconds
        print "b"

    I start the program
    I wait until the program has stopped
    I should see the printed output 'ab' or 'ba'

  Scenario: A waiting BroadcastAndWait brick is unblocked when the broadcast message is sent again.
    Object "test object" has the following scripts
      when program started
        broadcast "Print b after 0.3 seconds, and then c after another 0.5 seconds" and wait
        print "a"
      when program started
        wait 0.6 seconds
        broadcast "Print b after 0.3 seconds, and then c after another 0.5 seconds"
      when I receive "Print b after 0.3 seconds, and then c after another 0.5 seconds"
        wait 0.3 seconds
        print "b"
        wait 0.5 seconds
        print "c"

    I start the program
    I wait until the program has stopped
    I should see the printed output 'babc'

  Scenario: A waiting BroadcastWait brick is unblocked via another BroadcastWait brick.
    Object "test object" has the following scripts
      when program started
        broadcast "Print b after 5 seconds" and wait
        wait 1.0 seconds
        print "a"
      when program started
        wait 2.0 seconds
        broadcast "Print b after 5 seconds" and wait
        print "d"
      when I receive "Print b after 5 seconds"
        wait 5.0 seconds
        print "b"

    I start the program
    I wait until the program has stopped
    I should see the printed output 'abd'

  Scenario: A waiting BroadcastWait brick is unblocked when the same broadcast message is sent again and there are two WhenBroadcastReceived scripts responding to the same message.
    Object "test object" has the following scripts
      when program started
        broadcast "Print b and c from different scripts" and wait
        print "a"
      when I receive "Print b and c from different scripts"
        wait 0.4 seconds
        print "b"
      when I receive "Print b and c from different scripts"
        wait 0.5 seconds
        print "c"
      when program started
        wait 0.1 seconds
        broadcast "Print b and c from different scripts"
        wait 0.1 seconds
        print "d"

    I start the program
    I wait until the program has stopped
    I should see the printed output 'adbc'

  Scenario: A BroadcastWait brick repeatedly triggers a WhenBroadcastReceived script which is restarted immediately.
    Object "test object" has the following scripts
      when program started
        repeat 5 times
          broadcast "Send the BroadcastWait message"
          wait 2.0 seconds
        end of loop
      when I receive "Send the BroadcastWait message"
        broadcast "Print a, then b after 7 seconds" and wait
        print "c"
      when I receive "Print a, then b after 7 seconds"
        print "a"
        wait 7.0 seconds
        print "b"

    I start the program
    I wait until the program has stopped
    I should see the printed output 'aaaaabc'

  Scenario: A Broadcast brick repeatedly triggers a WhenBroadcastReceived script which is restarted immediately.
    Object "test object" has the following scripts
      when program started
        repeat 5 times
          broadcast "Send the Broadcast message"
          wait 2.0 seconds
          print "c"
          wait 1.0 seconds
        end of loop
      when I receive "Send the Broadcast message"
        broadcast "Print b after 0.5 seconds, then d after another 7 seconds"
        print "a"
      when I receive "Print b after 0.5 seconds, then d after another 7 seconds"
        wait 0.5 seconds
        print "b"
        wait 7.0 seconds
        print "d"

    I start the program
    I wait until the program has stopped
    I should see the printed output 'abcabcabcabcabcd'

  Scenario: A BroadcastWait brick is unblocked by a Broadcast brick. After that the BroadcastWait is triggered again.
    Object "test object" has the following scripts
      when program started
        repeat 2 times
          broadcast "Send the BroadcastWait message"
          wait 6.0 seconds
        end of loop
      when I receive "Send the BroadcastWait message"
        broadcast "Print a after 1 second, then b after another 5 seconds" and wait
        wait 2.0 seconds
        print "d"
      when program started
        wait 3.0 seconds
        broadcast "Print a after 1 second, then b after another 5 seconds"
        print "c"
      when I receive "Print a after 1 second, then b after another 5 seconds"
        wait 1.0 seconds
        print "a"
        wait 5.0 seconds
        print "b"

    I start the program
    I wait until the program has stopped
    I should see the printed output 'acadabd'

  Scenario: A BroadcastWait brick waits for two WhenBroadcastReceived scripts to finish and is unblocked by a Broadcast brick. After that the BroadcastWait is triggered again.
    Object "test object" has the following scripts
      when program started
        repeat 2 times
          broadcast "Print a and b from two different scripts" and wait
          wait 6.0 seconds
          print "c"
          wait 1.0 seconds
        end of loop
      when program started
        wait 2.0 seconds
        broadcast "Print a and b from two different scripts"
      when I receive "Print a and b from two different scripts"
        print "a"
      when I receive "Print a and b from two different scripts"
        wait 3.0 seconds
        print "b"

    I start the program
    I wait until the program has stopped
    I should see the printed output 'aabcabc'

  Scenario: Correct consecutive executions of one BroadcastWait brick.
    Object "test object" has the following scripts
      when program started
        repeat 2 times
          broadcast "Print a, then b after 1 second" and wait
          print "c"
        end of loop
      when I receive "Print a, then b after 1 second"
        print "a"
        wait 1.0 seconds
        print "b"

    I start the program
    I wait until the program has stopped
    I should see the printed output 'abcabc'

  Scenario: A BroadcastWait brick waits for a short and a long WhenBroadcastReceived script. The same BroadcastWait is triggered again before the long one finishes.
    Object "test object" has the following scripts
      when program started
        repeat 2 times
          broadcast "Send the BroadcastWait message"
          wait 3.0 seconds
        end of loop
      when I receive "Send the BroadcastWait message"
        broadcast "Print a immediately and b after 5 seconds" and wait
        print "c"
      when I receive "Print a immediately and b after 5 seconds"
        print "a"
      when I receive "Print a immediately and b after 5 seconds"
        wait 5.0 seconds
        print "b"

    I start the program
    I wait until the program has stopped
    I should see the printed output 'aabc'

  Scenario: A BroadcastWait brick sends a message and a different Object contains the corresponding WhenBroadcastReceived script.
    Object "test object" has the following script
      when program started
        repeat 2 times
          print "a"
          broadcast "Print b immediately and c after 3 seconds" and wait
          print "d"
        end of loop

    Object "2nd test object" has the following script
      when I receive "Print b immediately and c after 3 seconds"
        print "b"
        wait 3.0 seconds
        print "c"

    I start the program
    I wait until the program has stopped
    I should see the printed output 'abcdabcd'

  Scenario: A BroadcastWait brick waits for two WhenBroadcastReceived scripts to finish.
    Object "test object" has the following scripts
      when program started
        print "a"
        broadcast "Print b and c from two different scripts" and wait
        print "d"
      when I receive "Print b and c from two different scripts"
        print "b"
      when I receive "Print b and c from two different scripts"
        wait 0.3 seconds
        print "c"

    I start the program
    I wait until the program has stopped
    I should see the printed output 'abcd'

  Scenario: A Broadcast Wait brick waits for two WhenBroadcastReceived scripts from two different objects to finish.
    Object "test object" has the following script
      when program started
        repeat 2 times
          print "a"
          broadcast "Print b and c from two different objects" and wait
          print "d"
        end of loop

    Object "2nd test object" has the following script
      when I receive "Print b and c from two different objects"
        print "b"

    Object "3rd test object" has the following script
      when I receive "Print b and c from two different objects"
        wait 0.3 seconds
        print "c"

    I start the program
    I wait until the program has stopped
    I should see the printed output 'abcdabcd'

  Scenario: A Broadcast is sent after a BroadcastWait has finished.
    Object "test object" has the following script
      when program started
        print "a"
        broadcast "Print c" and wait
        print "b"

    Object "2nd test object" has the following script
      when program started
        wait 2.0 seconds
        print "d"
        broadcast "Print c"

    Object "3rd test object" has the following script
      when I receive "Print c"
        print "c"

    I start the program
    I wait until the program has stopped
    I should see the printed output 'acbdc'

  Scenario: A BroadcastWait is sent after a Broadcast has finished.
    Object "test object" has the following script
      when program started
        wait 2.0 seconds
        print "a"
        broadcast "Print c" and wait
        print "b"

    Object "2nd test object" has the following script
      when program started
        print "d"
        broadcast "Print c"

    Object "3rd test object" has the following script
      when I receive "Print c"
        print "c"

    I start the program
    I wait until the program has stopped
    I should see the printed output 'dcacb'
