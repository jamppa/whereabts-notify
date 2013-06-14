(ns whereabts-notify.handlers
	(:require [taoensso.carmine :as carmine]))

(def notify-user-on-reply-channel "notify-user-on-reply")

(def message-handlers
	{notify-user-on-reply-channel (fn handler [msg] (println msg))})