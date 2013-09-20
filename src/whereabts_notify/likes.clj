(ns whereabts-notify.likes
	(:use 
		whereabts-notify.gcm
		whereabts-notify.db.users))

(def like-message-type "TYPE_MESSAGE_LIKE")

(defn build-like-gcm-message [like-details]
	(let [message-owner-gcm-id (find-user-gcm-id (:message-owner-id like-details))
		  user-profile (find-profile-by-user-id (:user-id like-details))]

		  (gcm-message [message-owner-gcm-id] {
		  	:type like-message-type
		  	:message-id (:message-id like-details)
		  	:nick (:nick user-profile)})))

(defn liking-own-message? [like-details]
	(= (:user-id like-details) (:message-owner-id like-details)))

(defn notify-message-owner-on-like [like-details]
	(when-not (liking-own-message? like-details)
		(send-gcm-message (build-like-gcm-message like-details))))