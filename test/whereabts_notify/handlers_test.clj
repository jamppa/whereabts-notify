(ns whereabts-notify.handlers-test
	(:use 
		[midje.sweet]
		[whereabts-notify.handlers]))

(fact "should define message handlers so that it includes handler for notifying user on replies"
	message-handlers => {"notify-user-on-reply" notify-user-on-reply-handler})