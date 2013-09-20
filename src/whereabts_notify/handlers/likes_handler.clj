(ns whereabts-notify.handlers.likes-handler
	(:use 
		whereabts-notify.likes
		validateur.validation)
	(:require [taoensso.carmine :as carmine]))

(defn- details-from-msg [msg]
	(get msg 2))

(def like-details-validation
	(validation-set
		(presence-of :user-id)
		(presence-of :message-owner-id)
		(presence-of :message-id)))

(defn likes-handler [msg]
	(let [like-details (details-from-msg msg)]
		(when (valid? like-details-validation like-details)
			(notify-message-owner-on-like like-details))
		msg))