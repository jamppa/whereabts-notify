(ns whereabts-notify.handlers
	(:require [taoensso.carmine :as carmine]))

(def notify-user-on-reply-channel "notify-user-on-reply")

(defn notify-user-on-reply-handler [msg]
	)

(def message-handlers
	{notify-user-on-reply-channel notify-user-on-reply-handler})