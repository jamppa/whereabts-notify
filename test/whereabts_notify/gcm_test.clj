(ns whereabts-notify.gcm-test
	(:use 
		[whereabts-notify.gcm]
		[midje.sweet]))

(def registration-ids 
	["APA91bEkZK2rpVB4ivT8DTHq7wjFCNAbIRGE6YdqaXtK6t52KrooKRtvK75ALbZbXXBflEvo8WbxJgWyoQTfQvevmjutLIqlcAA0ETn7t2UaZgyg8dbn7tuFH86mJDWgILfgs21ROT0BNHO2iM6ttS0N_Kd_Zdzk1Q"])

(fact "should create message object for gcm"
	(gcm-message ["123AbC"] {:key "value"}) => {:registration_ids ["123AbC"] :data {:key "value"}})

(fact "should send message to google cloud messaging api"
	(:status (send-gcm-message (gcm-message registration-ids {}))) => 200)