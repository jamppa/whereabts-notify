(ns whereabts-notify.follow
	(:use 
		whereabts-notify.gcm
		whereabts-notify.db.users))

(def follow-message-type "TYPE_MESSAGE_NEW_FOLLOWER")

(defn follow-gcm-data [follower-profile]
	nil)

(defn build-follow-gcm-message [{follower-id :follower-id following-id :following-id}]
	(let [follower-profile (find-profile-by-user-id follower-id)
		  following-gcm-id (find-user-gcm-id following-id)]
		(gcm-message [following-gcm-id] (follow-gcm-data follower-profile))))

(defn notify-user-on-new-follower [follow-details]
	(-> 
		(build-follow-gcm-message follow-details)
		(send-gcm-message)))