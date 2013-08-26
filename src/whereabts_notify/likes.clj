(ns whereabts-notify.likes
	(:use whereabts-notify.gcm))

(def like-message-type "TYPE_MESSAGE_LIKE")

(defn build-like-gcm-message [like-details]
	nil)

(defn liking-own-message? [like-details]
	(= (:user-id like-details) (:message-owner-id like-details)))

(defn notify-message-owner-on-like [like-details]
	(when-not (liking-own-message? like-details)
		(send-gcm-message (build-like-gcm-message like-details))))