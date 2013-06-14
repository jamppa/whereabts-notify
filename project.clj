(defproject whereabts-notify "0.1.0-SNAPSHOT"
  :description "Notifies Whereabts users on various things"
  :url "http://whereabts.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
				[com.taoensso/carmine "1.12.0"]]
   :profiles {
      :dev {
         :dependencies [[midje "1.5.1"]]
         :plugins [[lein-midje "3.0.0"]]}}
  :main whereabts-notify.core)
