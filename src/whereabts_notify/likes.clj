(ns whereabts-notify.likes
	(:use whereabts-notify.gcm))

(def like-message-type "TYPE_MESSAGE_LIKE")

(defn notify-message-owner-on-like [like-details]
	nil)