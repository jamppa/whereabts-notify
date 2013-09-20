(ns whereabts-notify.follow
	(:use 
		whereabts-notify.gcm
		whereabts-notify.db.users))

(defn build-follow-gcm-message [follow-details]
	nil)

(defn notify-user-on-new-follower [follow-details]
	(-> 
		(build-follow-gcm-message follow-details)
		(send-gcm-message)))