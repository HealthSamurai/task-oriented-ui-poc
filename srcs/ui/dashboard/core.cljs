(ns ui.dashboard.core
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require
   [reagent.core :as reagent]
   [ui.pages :as pages]
   [ui.routes :refer [href]]
   [re-frame.core :as rf]
   [ui.widgets :as wgt]
   [ui.styles :as styles]
   [ui.editor :as editor]
   [clojure.string :as str]))

(defn index [params]
  [:div.container
   [:h1 "Dashboard"]])

(defn avatar [& [x]]
  [:img.avatar {:src (str "https://randomuser.me/api/portraits/men/" (or x (rand-int 100)) ".jpg")}])

(defn patient-bgt [opened]
  [:div
   (styles/style
    [:div
     [:.navmenu {:border "1px solid #ddd" :padding "20px" :position "relative" :top "-1px" :border-radius "4px"
                 :box-shadow "1px 1px 3px #ddd"}
      [:b {:font-weight 300 :margin "20px 0 10px 0" :display "block" :font-size "14px" :color "gray" :border-bottom "1px solid #ddd"}]
      [:.item {:border "none!important" :padding-left 0}]
      ]
     [:.item {:padding "5px 0"
              ;; :border-left "2px solid #8bc34a"
              :border-left "2px solid #ddd"
              :padding-left "15px"
              :margin-bottom "10px"
              :color "#555"
              :display "block"}
      [:&.active {:border-color "#666"}]
      [:.time {:color "gray" :margin-right "15px"}]
      [:&.critical {:border-color "pink"}]
      [:&:hover {:background "#f6f6f6"}]
      [:b {:font-weight "300" :color "gray"}]
      [:.fa {:color "gray"  :margin-right "10px"}]
      [:.title {:margin "0px"}]]
     [:.pt
      [:.info
       [:img.avatar {:width "80px" :height "80px"
                     :border "1px solid #ddd"
                     :margin-top "20px" :border-radius "50%" :margin-right "20px" :float "left"}]
       [:.data {:margin-left "100px"}]]
      [:.tabs {:border-bottom "1px solid #ddd" :margin-top "20px"}
       [:.tab {:display "inline-block" :margin-right "20px" :color "#888"
               :border-bottom "2px solid transparent"
               :line-height "30px"
               :position "relative"
               :bottom "-1px"
               }
        [:&.active {:border-bottom "2px solid #666" :color "black"}]
        [:b {:color "black"
             :font-size "12px"
             :display "inline-block"
             :background "#f6f6f6;"
             :width "20px"
             :height "20px"
             :text-align "center"
             :line-height "20px"}]
        ]]]
     [:.option {:font-weight 300 :color "#888" :margin-right "15px"}]])

   [:div.pt
    [:div.info
     (avatar)
     [:div.data
      [:h3
       [:span.action [wgt/icon :thumb-tack]]
       "Jonh Smith " [:span.small "Male 37y"]
       " "
       [:span.action "Chat"]
       [:span.action "Task"]
       [:span.action "Call"]]
      [:p "Some information"]
      ]]
    [:div.tabs
     [:div.tab.active "Current Visit " [:b 5]]
     [:div.tab "Visits " [:b 5]]
     [:div.tab "Issues " [:b 1]]
     [:div.tab {:on-click #(swap! opened (fn [x] (not x)))} "More... " [wgt/icon :chevron-down]]]]
(when @opened
         [:div.navmenu
          [wgt/icon :search] [:input.search {:type "search" :placeholder "search..."}]
          [:br]
          [:br]
          [:div.row
           [:div.col-6
            [:b "Pt nav"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Facesheet"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Allergies" [:span.action [wgt/icon :thumb-tack]]]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Observations"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Medications"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Lab Tests"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Documents"]
            ]
           [:div.col-6
            [:b "Visit nav"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Init. Assessment"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Assessment"]
            [:a.item.active {:href (href "claims")} [wgt/rand-icon] "Notes"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Discharge"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Vitals"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Review Of Systems"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Past Medical History"]
            ]

           ]
          [:div.row
           [:div.col-6
            [:b "Pt nav"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Facesheet"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Allergies" [:span.action [wgt/icon :thumb-tack]]]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Observations"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Medications"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Lab Tests"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Documents"]
            ]
           [:div.col-6
            [:b "Visit nav"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Init. Assessment"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Assessment"]
            [:a.item.active {:href (href "claims")} [wgt/rand-icon] "Notes"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Discharge"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Vitals"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Review Of Systems"]
            [:a.item {:href (href "claims")} [wgt/rand-icon] "Past Medical History"]]]])])

(defn user [name & [not-link?]]
  (if not-link?
    [:span.assignee
     (avatar)
     name]
    [:a.assignee
     {:href (href :patients)}
     (avatar)
     name]))

(defn notes [params]
  (rf/dispatch [:cm/set-val :cm-test "This is a 21 year old female who comes in for a chief complaint of acne, located on the face. The
acne consists of scarring and pimples, is moderate in severity and has been present for years. Pertinent
history includes: history of accutane use, an oily face, wearing make up, and normal menses. Pertinent
negatives include: acne does not worsen around menstrual cycle, no oral contraceptive use, does not pick
at acne, no history of radiation treatment in the past for acne, and does not have a dry face. She has tried:
Isotretinoin. She is not currently on any treatment.
"])
  (fn [params]
    [:div.ws
     (styles/style
      [:div.center
       {:width "700px" :margin "0 auto"}
       [:h1 {:border-bottom "none!important"
             :font-size "25px"
             :color "#666"
             :margin-top "20px"
             :margin-bottom "-10px"
             :font-weight "bold"}]
       [:.option {:font-weight 300 :color "#888" :margin-right "15px"}]
       [:.activities {}]
       [:.history {:border-left-color "#ddd"}]
       [:.time {:color "gray" :margin-right "20px"}]
       [:.assignee {:margin-left 0 :margin-top "4px"}]
       [:.item {:padding "5px 0"
                ;; :border-left "2px solid #8bc34a"
                :border-left "2px solid #ddd"
                :padding-left "15px"
                :margin-bottom "10px"
                :color "#555"
                :display "block"}
        [:&.critical {:border-color "pink"}]
        [:&:hover {:background "#f6f6f6"}]
        [:b {:font-weight "300" :color "gray"}]
        [:.fa {:color "gray"  :margin-right "10px"}]
        [:.title {:margin "0px"}]]])
     [:div.center
      (styles/style
       [:div
        [:b {:font-weight 300 :margin "20px 0 10px 0" :display "block" :font-size "14px" :color "gray" :border-bottom "1px solid #ddd"}]
        [:.nowrap {:white-space "nowrap"}]
        [:.desc {:color "#888"}]
        [:.pt
         [:.info
          [:img.avatar {:width "80px" :height "80px"
                        :border "1px solid #ddd"
                        :margin-top "20px" :border-radius "50%" :margin-right "20px" :float "left"}]
          [:.data {:margin-left "100px"}]]]
        [:.option {:font-weight 300 :color "#888" :margin-right "15px"}]
        [:.text :.CodeMirror
         {:border-left "4px solid #eee"
          :width "100%"
          :min-height "auto"
          :font-family  "medium-content-serif-font,Georgia,Cambria,\"Times New Roman\",Times,serif"
          :letter-spacing ".01rem;"
          :font-weight 400;
          :font-style "normal;"
          :color "rgba(0,0,0,.8)"
          :font-size "18px;"
          :line-height 1.58;
          :padding "0px 20px"}
         [:&:focus
          {:box-shadow "none!important"
           :outline "none"
           :border-left-color "#26b4f4"}]]
        [:.CodeMirror {:height "auto"}]])
      [patient-bgt (atom false)]
      #_[:div.pt
         [:div.info
          (avatar)
          [:div.data
           [:br]
           [:h3
            "Anna Khan " [:span.small "Female 21y"]
            " "
            [:span.action [wgt/icon :thumb-tack]]]
           [:p "Some information"]]]]
      [:section
       [:h1 "CC: " "Acne"]]

      [:section
       [:h1 "Summary"]
       (styles/style [:section {:position "relative"}
                      [:.actions {:position "absolute"
                                  :right "-350px"
                                  :top "-0px"
                                  :width "300px"
                                  :border "1px solid #ddd"
                                  :border-radius "4px"
                                  :padding "20px 15px"
                                  :box-shadow "1px 1px 2px #ddd"
                                  }
                       [:a.itemo {:display "block" :padding "5px 0"
                                  :cursor "pointer"
                                  :color "#1b95e0!important"}]
                       ]])
       [:div.actions
        [:h3 "Summary templates"]
        [:a.itemo "Acne template 1"]
        [:a.itemo "Acne template 1"]
        [:a.itemo "Acne template 1"]
        [:a.itemo "Acne template 1"]
        [:a.itemo "Acne template 1"]

        ]
       [editor/superior-cm
        {:complete-fn (partial editor/complete-startswith
                               #"#"
                               {"test" [{:displayText "first" :text "first"}
                                        {:displayText "nikolai"
                                         :text "Божиею поспешествующею милостию, Мы, Николай Вторый, Император и Самодержец Всероссийский, Московский, Киевский, Владимирский, Новгородский; Царь Казанский, Царь Астраханский, Царь Польский, Царь Сибирский, Царь Херсониса Таврическаго, Царь Грузинский; Государь Псковский и Великий Князь Смоленский, Литовский, Волынский, Подольский и Финляндский; Князь Эстляндский, Лифляндский, Курляндский и Семигальский, Самогитский, Белостокский, Корельский, Тверский, Югорский, Пермский, Вятский, Болгарский и иных; Государь и Великий Князь Новагорода низовския земли, Черниговский, Рязанский, Полотский, Ростовский, Ярославский, Белозерский, Удорский, Обдорский, Кондийский, Витебский, Мстиславский и всея северныя страны Повелитель; и Государь Иверския, Карталинския и Кабардинския земли и области Арменския; Черкасских и Горских Князей и иных Наследный Государь и Обладатель; Государь Туркестанский; Наследник Норвежский, Герцог Шлезвиг-Голстинский, Стормарнский, Дитмарсенский и Ольденбургский и прочая, и прочая, и прочая"}]
                                "test2" ["another" "completion"]})
         :value @(rf/subscribe [:cm/get-val :cm-test])
         :on-change #(rf/dispatch [:cm/set-val :cm-test %])}]]
      [:section
       [:h1 "HPI"]
       [:div.text
        "This is a 21 year old female who comes in for a chief complaint of acne, located on the face. The acne consists of scarring and pimples, is moderate in severity and has been present for years. Pertinent history includes: history of accutane use, an oily face, wearing make up, and normal menses. Pertinent negatives include: acne does not worsen around menstrual cycle, no oral contraceptive use, does not pick at acne, no history of radiation treatment in the past for acne, and does not have a dry face. She has tried: Isotretinoin. She is not currently on any treatment. Was on isotretinoin x 3 mos while living in Pakistan. Was clear for a short time but acne recurred. Regular menses (qmo) but acne occurs almost daily Pt took isotretinoin for 3 months in 2016 from Pakistan which improved her acne. However, acne is recurring. Pt has also tried OTC topical tx (vitamin C serum) which helped initially. Pt has regular menses "]]

      [:section.ros
       [:h1 "ROS"]
       (styles/style [:.ros
                      [:h4 {:font-size "13px" :font-weight "300"}]
                      [:.itema {:border-left "5px solid #ddd"
                                :color "#888"
                                :margin-bottom "5px"
                                :padding "0 10px"}
                       [:&.active {:border-color "green"}]
                       ]])
       [:div.row
        [:div.col-4
         [:h4 "Integumentary"]
         [:div.itema "problems with healing"]
         [:div.itema.active "problems with scarring (hypertrophic or keloid)"]
         [:div.itema "rash"]
         ]
        [:div.col-4
         [:h4 "Allergic / Immunologic"]
         [:div.itema "immunosuppression	Allergic / Immunologic"]
         [:div.itema.active "hay fever"]
         [:div.itema "problems with healing"]]
        [:div.col-4
         [:h4 "Integumentary"]
         [:div.itema "problems with healing"]
         [:div.itema "problems with scarring (hypertrophic or keloid)"]
         [:div.itema "rash"]]]

       ]

      [:section
       [:h1 "Exam"]
       [:div.text
        "An examination was performed including the head (including face), inspection of conjunctivae and lids,
lips but not teeth and gums, right ear, left ear, neck, chest, back, right forearm, left forearm, right hand,
and left hand.
Patient Skin Type is Type IV.
General Appearance of the patient is well developed and well nourished.
Orientation: alert and oriented x 3.
Mood and affect: in no acute distress.
 - acne vulgaris on the left inferior medial malar cheek, right medial superior chest, and superior thoracic
spine.
 - post inflammatory pigmentary change on the right inferior central malar cheek and left inferior lateral
malar cheek.
 - inflammatory papules and pustules, comedonal papules, scars, and PIH
 - ill-defined hyperpigmented patches"]]

      [:section
       [:h1 "Impression/Plan: "
        "Acne Vulgaris (New Dx) (L70.0)"]
       [:div.text "Overall Assessment: 3.0 - multiple inflammatory lesions but noninflammatory lesions predominate"]]

      [:section
       [:h1 "Notes " ]
       [:div.text "(+) Hx of taking isotretinoin for 3 months in 2016"]]

      [:section
       [:h1 "Prescriptions" ]
       [:table.table
        [:tbody
         [:tr
          [:td.nowrap [:strong "benzoyl peroxide 5 % "] [:br] "topical cleanser"]
          [:td.desc "Wash face, chest, and back once daily in the shower. Leave on for 10-15 sec before rinsing. Rinse thoroughly"]]
         [:tr
          [:td.nowrap [:strong "tretinoin 0.05 %"] [:br] "topical cream"]
          [:td.desc "Apply a lentil sized amount to the entire face once in the evening. Start 2x weekly and increase application to every night as tolerated"]]]]
       [:div {:style {:background-color "#f1f1f1" :border-left "4px solid #ddd" :padding "20px" :margin "20px 0"}}
        "Send prescription to Pharmacy: "
        [:strong "First Pharmacy 4167 Bowne St (718) 463-0333 "]
        [:button.btn.btn-primary "Send!"]]

       [:div.text
        "Will tx with topicals for now. May consider PO medication on f/u visit if condition is inadequately controlled or worsens. Pt was
counseled that AV may be hormonal in nature.."
        ]
       ]
      [:section
       [:h1 "Counseling" ]
       [:div.text "
I counseled the patient regarding the following:
Skin care: I discussed with the patient the importance of using cleansers, moisturizers and cosmetics that are non-comedogenic.
Expectations: The patient is aware that acne is a chronic condition. It may take up to 2-3 months to see a 60-80% improvement of
acne. Main goal of acne treatment is to prevent permanent scars.
Contact office if: Acne worsens or fails to improve despite months of treatment; patient develops new scars, significantly more
nodules or cysts.
Sun protection emphasized.
Nutrition (be wary of high GI foods and dairy), exercise, stress mgmt, PO hydration,
"]]
      [:section
       [:h1 "Isotretinoin Counseling" ]
       [:div.text
        "Patient should be on two forms of birth control, get monthly blood tests, not donate blood, not drive at night
it vision affected, not share medication, and not undergo surgery for 6 months after tx completed. Side effects reviewed, pt to
contact office should one occur."]]
      [:section
       [:h1 "Doxycycline Counseling" ]
       [:div.text
        "Patient counseled regarding possible photosensitivity and increased risk for sunburn. Patient instructed
to avoid sunlight, if possible. When exposed to sunlight, patients should wear protective clothing, sunglasses, and sunscreen. The
patient was instructed to call the office immediately if the following severe adverse effects occur: hearing changes, easy
bruising/bleeding, severe headache, or vision changes. The patient verbalized understanding of the proper use and possible
adverse effects of doxycycline. All of the patient's questions and concerns were addressed. Patient understands to avoid
pregnancy while on therapy due to potential birth defects.
"]]
      [:section
       [:h1 "Location" ]
       [:img {:src "https://www.acne.org/img/body-acne-illustration.jpg"}]
       [:img {:src "https://www.acne.org/img/body-acne-illustration.jpg"}]]

      [:section
       [:h1 "Photos" ]
       [:img {:style {:width "200px" :margin-right "20px"} :src "http://www.cmcnsw.com.au/wp-content/uploads/2013/06/Acne-1.jpg"}]
       [:img {:style {:width "200px" :margin-right "20px"} :src "http://www.cmcnsw.com.au/wp-content/uploads/2013/06/Acne-1.jpg"}]
       [:img {:style {:width "200px"} :src "http://www.cmcnsw.com.au/wp-content/uploads/2013/06/Acne-1.jpg"}]]

      [:hr]
      [:section
       [:h1 "Post Inflammatory Pigmentary Change (L81.0)" ]
       [:div.text
        "I counseled the patient regarding the following:
Skin care: Recommend minimizing sun exposure, wearing sunscreen and protective clothing.
Expectations: Post Inflammatory pigmentary change is lighter or darker discoloration than surrounding skin resulting from trauma or
rashes. Areas tend to normalize over time, but can take months to years.
Contact office if: PIH worsens, or spread to other parts of the body.
Will take time to resolve, observation recommended. Photo protection recommended.
Treat underlying causative condition."]]

      [:hr]
      [:section
       [:h1 "Follow Up in 1 month with Dr. Sun" ]
       [:img {:src "http://www.titanui.com/wp-content/uploads/2013/05/29/Pink-and-White-Flat-Calendar-Widget-PSD.jpg"}]]


      ]]))

(defn tasks [params]
  [:div.center
   (styles/style
    [:div.center
     {:width "900px"
      :margin "0 auto"}
     [:.option {:font-weight 300 :color "#888" :margin-right "15px"}]
     [:.activities {}]
     [:.history {:border-left-color "#ddd"}]
     [:.time {:color "gray" :margin-right "20px"}]
     [:.assignee {:margin-left 0 :margin-top "4px"}]
     [:.item {:padding "5px 0"
              ;; :border-left "2px solid #8bc34a"
              :border-left "2px solid #ddd"
              :padding-left "15px"
              :margin-bottom "10px"
              :color "#555"
              :display "block"}
      [:&.critical {:border-color "pink"}]
      [:&:hover {:background "#f6f6f6"}]
      [:b {:font-weight "300" :color "gray"}]
      [:.fa {:color "gray"  :margin-right "10px"}]
      [:.title {:margin "0px"}]]])
   [:div.activities
    [:h3 "My tasks"
     [:span.search-icon [wgt/icon :search]] [:input.search {:type "search" :placeholder "search..."}]]

    [:span.action "New Task"]
    [:span.option [wgt/icon :check-square-o] " My tasks"]
    [:span.option [wgt/icon :check-square-o] " Team tasks"]
    [:span.option [wgt/icon :check-square-o] " All realted"]
    [:br]
    [:br]

    [:a.item
     [wgt/rand-icon]
     [:a.title {:href (href :tasks :claim-submit)} "Submit claims for 08/23/17"]
     [:br]
     [:b "Assigned: "] (user "Nikolai Lee")
     [:b "Status: "] "in-progress"
     ]
    [:a.item
     [wgt/rand-icon]
     [:a.title {:href (href :tasks :claim-report)}
      "Billing report for July"]
     [:span
      " ›"]
     [:br]
     [:b "Assigned: "] (user "Will Smith")
     [:b "Status: "] "in-progress"
     ]

    [:a.item.critical
     [wgt/rand-icon]
     [:a.title {:href (href :tasks :check-claim)}
      "Check claim for Drago Ivankovichi 08/31/17"]
     [:br]
     [:b "Assigned: "] (user "John Ivanov")
     [:b "Status: "] "in-progress"]

    [:a.item
     [wgt/rand-icon]
     [:a.title {:href (href :tasks :claim-report)}
      "Billing report for July"]
     [:br]

     [:b "Assigned: "] (user "Will Smith")
     [:b "Status: "] "in-progress"
     ]
    [:a.item
     [wgt/rand-icon]
     [:a.title {:href (href :tasks :claim-era)}
      "Check ERA for John Johanson for 08/01/17"]
     [:br]
     [:b "Assigned: "] (user "Andrew Dunston")
     [:b "Status: "] "in-progress"
     ]
    ]

   [:br]
   [:div.history
   [:h3 "History"]

    (for [i (range 50)]
      (if (= i 4)
        [:a.item {:style {:border-color "#666"}}
         [:div.title
          [:span.time "10:15"]
          [wgt/rand-icon]
          [:a.title {:href (href :tasks :claim-report)}
           "Billing report for July"]
          (user "Andrew Dunston")]
         [:div {:style {:padding-left "10px"}}
          [:br]
          [:h3 "Results of"]
          [:p "Contribute to shadow-cljs development by creating an account on GitHub. ... The compiler will produce one output file named public/assets/app/js/main.js .... :modules may also be used to generate .js file intended to be used as a Web Worker."]
          [:br]
          [:a {:href "#" :style {:color "#03A9F4"}} "Read more..."]]]

        [:a.item
         [:span.time "10:15"]
         [wgt/rand-icon]
         [:a.title {:href (href :tasks :claim-report)}
          "Billing report for July"]
         (user "Andrew Dunston")]))]
   ])

(defn form []
  [:form.form.block
   (styles/style
    [:.form
     {:padding "20px"}
     [:.form-row {:margin-bottom "10px"}]
     [:label {:color "#888" :width "200px"  :text-align "right" :margin-right "10px"}]
     [:input {:border-top "none"
              :border-left "none"
              :border-right "none"
              :padding "5px 10px"
              :margin-left "10px"
              :border-bottom "1px solid #aaa"}]])
   [:h1 "Claim form"]
   [:h3 "Patient Info"]
   [:div.form-row
    [:label "Patient Name"]
    [:input {:type "text"
             :placeholder "Family"}]
    [:input {:type "text"
             :placeholder "Given"}]]
   [:div.form-row
    [:label "Patient Address"]
    [:input {:type "text"
             :placeholder "Streat"}]
    [:input {:type "text"
             :placeholder "City"}]
    [:input {:type "text"
             :placeholder "ZIP"}]]
   [:h3 "Insurance Info"]
   [:div.form-row
    [:label "Insurance Type"]
    [:input {:type "text"
             :placeholder "Company"}]
    [:input {:type "text"
             :placeholder "Given"}]]
   [:div.form-row
    [:label "Insurance Type"]
    [:input {:type "text"
             :placeholder "Company"}]
    [:input {:type "text"
             :placeholder "Given"}]]
   [:div.form-row
    [:label "Insurance Type"]
    [:input {:type "text"
             :placeholder "Company"}]
    [:input {:type "text"
             :placeholder "Given"}]]

   [:h3 "Services"]
   [:div.form-row
    [:input {:type "text" :placeholder "Code"}]
    [:input {:type "text" :placeholder "Description"}]
    [:input {:type "text" :placeholder "Diagnosis"}]
    [:input {:type "text" :placeholder "Amount"}]
    " "
    [:button.btn.btn-default  " +"]]
   [:div.form-row
    [:input {:type "text" :placeholder "Code"}]
    [:input {:type "text" :placeholder "Description"}]
    [:input {:type "text" :placeholder "Diagnosis"}]
    [:input {:type "text" :placeholder "Amount"}]
    " "
    [:button.btn.btn-default  " +"]]
   [:div.form-row
    [:input {:type "text" :placeholder "Code"}]
    [:input {:type "text" :placeholder "Description"}]
    [:input {:type "text" :placeholder "Diagnosis"}]
    [:input {:type "text" :placeholder "Amount"}]
    " "
    [:button.btn.btn-default  " +"]]
   [:hr]
   [:button.btn.btn-primary  "Update"]

   ]
  )

(defn claim-check [params]
  [:div.task-pane
   (styles/style
    [:div.task-pane {}
     [:div.task-bar {:position "absolute"
                     :left "90px"
                     :width "400px"
                     :padding "20px"
                     :bottom 0
                     :border-left "1px solid #ddd"
                     :top 0
                     :background-color "#f6f6f6"}]
     [:.task {}
      [:b {:font-weight 300 :color "#888"}]]
     [:div.pane {:width "900px" :margin-left "500px"}
      [:.form {:border-left-color "#aaa" :background-color "#f1f1f1"}]]])
   [:div.pane
    [patient-bgt (reagent/atom false)]
    [:br]
    [:div [:a.action "Claims"] "|" [:a.action "No: 168"]]
    (form)]
   [:div.task-bar
    [:div.task
     [:h3 [:b "Task:"] " Check Claim" [:span.action  "Leave task"]]
     [:b "Severity: "] "Normal"
     [:br]
     [:b "Assignee: " (user "Adolf Lungren")]
     [:br]
     [:b "Actions "]
     [:a.action {:href "#/me"} "Assign to me"]
     " | "
     [:a {:href "#/me"} " Change"]]

    [:br]
    [:div
     [:h3  [:a.action "Insurances"]]
     [:ul
      [:li "Medicare " [:b "primary"]]
      [:li "Some specific insurance"]]]

    [:div
     [:h3    [:a.action "Claim History "]]
     [:ul
      [:li "Service 1 02/10/2017 - 2.000$"]
      [:li "Service 1 02/10/2017 - 2.000$"]
      [:li "Service 1 02/10/2017 - 2.000$"]]]

    ]
   ])

(defn claim-submit [params]
  [:div
   (styles/style [:div
                  [:.claim {:border-left-color "#888"}]
                  [:.task {:border-left-color "#0275d8"}]])
   [:div.task.block
    [:h3 [:b "Task:"] " Submit claims for " [:i "08/01/17"]]
    [:b "Start Time: "] "11:00"
    [:br]
    [:b "Status: "] "In Progress"
    [:br]
    [:b "Severity: "] "Normal"
    [:br]
    [:b "Assignee: " (user "Adolf Lungren")]
    [:a {:href "#/me"} "Assign to me"]
    " | "
    [:a {:href "#/me"} " Change"]
    [:br]
    [:br]
    [:a.btn.btn-primary {:href (href "tasks")} "Submit"]]
   [:br]
   [:div.claim.block
    [:table.table
     [:thead
      [:tr
       [:td [:input {:type "checkbox" :name "group"}]]
       [:th "Ticket"]
       [:th "Name"]
       [:th "Insurance"]
       [:th "Services"]]]
     [:tbody
      [:tr
       [:td [:input {:type "checkbox" :name "group"}]]
       [:td "23333f"]
       [:td "Maria Fedotova"]
       [:td "Medicare"]
       [:td {:style {:padding 0}}
        [:table.table.table-striped.table-sm
         [:thead
          [:th "date"]
          [:th "procedure"]
          [:th "diagnosis"]
          [:th "charge"]]
         [:tbody
          [:tr
           [:td "08/01/2017"]
           [:td "88323"]
           [:td "L718, D2239"]
           [:td "170.7 x 2"]]
          [:tr
           [:td "08/01/2017"]
           [:td "88323"]
           [:td "L718, D2239"]
           [:td "170.7 x 2"]]]]]]
      [:tr
       [:td [:input {:type "checkbox" :name "group"}]]
       [:td "23333f"]
       [:td "Maria Fedotova"]
       [:td "Medicare"]
       [:td {:style {:padding 0}}
        [:table.table.table-striped.table-sm
         [:thead
          [:th "date"]
          [:th "procedure"]
          [:th "diagnosis"]
          [:th "charge"]]
         [:tbody
          [:tr
           [:td "08/01/2017"]
           [:td "88323"]
           [:td "L718, D2239"]
           [:td "170.7 x 2"]]
          [:tr
           [:td "08/01/2017"]
           [:td "88323"]
           [:td "L718, D2239"]
           [:td "170.7 x 2"]]]]]]
      [:tr
       [:td [:input {:type "checkbox" :name "group"}]]
       [:td "23333f"]
       [:td "Maria Fedotova"]
       [:td "Medicare"]
       [:td {:style {:padding 0}}
        [:table.table.table-striped.table-sm
         [:thead
          [:th "date"]
          [:th "procedure"]
          [:th "diagnosis"]
          [:th "charge"]]
         [:tbody
          [:tr
           [:td "08/01/2017"]
           [:td "88323"]
           [:td "L718, D2239"]
           [:td "170.7 x 2"]]
          [:tr
           [:td "08/01/2017"]
           [:td "88323"]
           [:td "L718, D2239"]
           [:td "170.7 x 2"]]]]]]]]]])

(defn patient []
  [:div.patient.block
   (styles/style
    [:.patient
     {:border-left-color "#03A9F4"}
     [:b {:color "gray" :font-weight "300"}]
     [:img.avatar {:width "20px" :border "1px solid #ddd" :border-radius "50%"}]])

   [:h3 "Patient: " (avatar) " Ivan Mccalohen " [:span.small [wgt/icon :male] " 37 years old"]]
   [:padding-left
    [:b "Phone: "] " 7777-77667"
    [:br]
    [:b "Address: "] " 1419 Westwood Blvd - Los Angeles CA"]])

(defn visit []
  [:div.visit.block
   (styles/style
    [:.visit
     {:border-left-color "#FFC107"}
     [:b {:color "gray" :font-weight "300"}]
     [:img.avatar {:width "18px"
                   :height "18px"
                   :margin "0 5px"
                   :border "1px solid #ddd"
                   :display "inline-block"
                   :border-radius "50%"}]])
   [:h3 "Visit: Cryotherapy" [:span.small " 08/01/2017"]]
   [:p
    [:b "Physician: "] (user "Dr. House")]])

(defn claim-era [params]
  [:div
   (styles/style [:div
                  [:.pane {:width "900px"}]
                  [:.ctx {:position "fixed"
                          :width "400px"
                          :top 0
                          :right 0
                          :bottom 0
                          :padding "30px"
                          :background-color "#f6f6f6"}]
                  [:.era {:border-left-color "#F44336"}]
                  [:.task {:border-left-color "#0275d8"}]])
   [:div.pane
    [:div.task.block
     [:h3 "Review ERA for Ivan Maccalohen visit"]
     [:b "Start Time: "] "11:00"
     [:br]
     [:b "Status: "] "In Progress"
     [:br]
     [:b "Severity: "] "Normal"
     [:br]
     [:b "Assignee: " (user "Adolf Lungren")]
     [:a {:href "#/me"} "Assign to me"]
     " | "
     [:a {:href "#/me"} " Change"]]
    [:br]
    [:div.era.block
     [:h3 "Claim was rejected"]
     [:p "Regardless of how brilliant a medical biller is, they are guaranteed to come across rejections and denials from time to time. These terms are frequently used to discuss medical billing claims and are often used interchangeably by even the most experienced team members in the health field. However, a rejection differs vastly from a denial."]

     [:h3 "Possible actions:"]
     [:a.btn.btn-primary {:href (href "tasks")} "Submit"]]
    (form)]
   [:div.ctx
    [patient]
    [visit]]])

(defn task [params]
  [:div
   (get
    {"claim-submit" [claim-submit params]
     "check-claim" [claim-check params]
     "claim-era" [claim-era params]
     }
    (:type params))])


(def nav-style
  [:div
   [:.navigator
    {:position "absolute"
     :left "90px"
     :top 0
     :bottom 0
     :padding-top "30px"
     :background "#f6f6f6"
     :padding-left "0px"
     :padding-right "10px"
     :width "190px"}
    [:h3 {:border-bottom "1px solid rgba(0,0,0,.15)!important"
          :font-size "20px"
          :line-height "40px"}]
    [:.item {:display "block"
             :padding "5px 10px"
             :color "#666"
             :font-weight 300}
     [:.fa {:width "24px"}]
     [:&.active {:color "#444" :font-weight "600"}]
     ]
    [:.switch {:background-color "#eee"}
     [:.fa {:float "right" :color "#ccc" :padding-top "2px"}]
     ]
    [:.block {:padding-left "15px"}]
    [:a.assignee {:border "none" :padding-left 0}]]
   [:.pane {:padding-top "10px" :margin-left "220px"}]])

(defn navigation-old [params]
  [:div
   (styles/style nav-style)
   [:div.navigator
    [:a.item {:href (href "claims")} [wgt/rand-icon] " Patients"]
    [:a.item {:href (href "claims")} [wgt/rand-icon] " Claims"]
    [:a.item {:href (href "claims")} [wgt/rand-icon] "Statistic"]
    [:a.item.active {:href (href "claims")} [wgt/rand-icon] " Visits"]
    [:hr]
    [user "Miky Mikluha"]
    [user "John Jonson"]
    [user "Alfred Muha"]
    [user "Pablo Picaso"]
    [user "Salvador Dali"]
    [user "John Jonson"]

    ]

   [:div.visits.pane
    [:h1 "Visits  " ]
    [:br]
    [:br]
    [:table.table
     [:tbody
      [:tr
       [:td.mute "08/01/17 10:00"]
       [:td (user "Luis Borhes")]
       [:td " Neurotoxins"]]
      [:tr
       [:td.mute "08/01/17 11:00"]
       [:td (user "Joao Jilberto")]
       [:td "Neurotoxin injectable for skin rejuvenation"]]
      [:tr
       [:td.mute "08/01/17 11:00"]
       [:td (user "Augustiniana Johanson")]
       [:td "Neurotoxin injectable for skin rejuvenation"]]
      [:tr
       [:td.mute "08/01/17 10:00"]
       [:td (user "Luis Borhes")]
       [:td " Neurotoxins"]]
      [:tr
       [:td.mute "08/01/17 11:00"]
       [:td (user "Joao Jilberto")]
       [:td "Neurotoxin injectable for skin rejuvenation"]]
      [:tr
       [:td.mute "08/01/17 11:00"]
       [:td (user "Augustiniana Johanson")]
       [:td "Neurotoxin injectable for skin rejuvenation"]]


      ]

     ]

    ]

   ])
(defn navigation [params]
  [:div.navigation
   (styles/style nav-style)
   [styles/style
    [:.navigation {:width "900px" :margin "0 auto"}
     [:.item {:padding "5px 0"
              ;; :border-left "2px solid #8bc34a"
              :border-left "2px solid #ddd"
              :padding-left "15px"
              :margin-bottom "10px"
              :color "#555"
              :display "block"}
      [:img.avatar {:width "40px" :height "40px" :border-radius "50%" :margin-right "10px"}]
      [:&.critical {:border-color "pink"}]
      [:&:hover {:background "#f6f6f6"}]
      [:b {:font-weight "300" :color "gray"}]
      [:.fa {:color "gray"  :margin-right "10px"}]
      [:.action [:.fa {:color "gray"  :margin-right "0px"}]]
      [:.title {:margin "0px"}]]

     ]
    ]

   [:h3 "Navigation "
    [:span.search-icon [wgt/icon :search]] [:input.search {:type "search" :placeholder "search..."}]]
   [:br]
   [:br]
   [:a.item {:href (href "claims")} [wgt/rand-icon] "Patients"      [:span.action [wgt/icon :thumb-tack]]]
   [:a.item {:href (href "claims")} [wgt/rand-icon] "Appointments"  [:span.action [wgt/icon :thumb-tack]]]
   [:a.item {:href (href "claims")} [wgt/rand-icon] "Visits"]
   [:a.item {:href (href "claims")} [wgt/rand-icon] "Claims"        [:span.action [wgt/icon :thumb-tack]]]
   [:a.item {:href (href "claims")} [wgt/rand-icon] "Statistic"     [:span.action [wgt/icon :thumb-tack]]]
   [:a.item {:href (href "claims")} [wgt/rand-icon] "Tasks"]
   [:a.item {:href (href "claims")} [wgt/rand-icon] "Reports"]
   [:br]
   [:h5 "Recent Visits/Patients"]
   [:br]
   [:a.item {:href (href "patients")} (avatar) "John Smith"  [:span.action [wgt/icon :thumb-tack]]]
   [:a.item {:href (href "patients")} (avatar) "John Smith"  [:span.action [wgt/icon :thumb-tack]]]
   [:a.item {:href (href "patients")} (avatar) "John Smith"  [:span.action [wgt/icon :thumb-tack]]]
   ])



(defn patients [params]
  (let [opened (reagent/atom false)]
    (fn [params]
      [:div.patients
       (styles/style
        [:.patients {:width "900px" :margin "0 auto"}
         [:.item {:padding "5px 0"
                  ;; :border-left "2px solid #8bc34a"
                  :margin-bottom "10px"
                  :color "#555"
                  :display "block"}
          [:&.critical {:border-color "pink"}]
          [:&:hover {:background "#f6f6f6"}]
          [:b {:font-weight "300" :color "gray"}]
          [:.fa {:color "gray"  :margin-right "10px"}]
          [:.action [:.fa {:color "gray"  :margin-right "0px"}]]
          [:.title {:margin "0px"}]]

         [:.item {:padding "5px 0"
                  ;; :border-left "2px solid #8bc34a"
                  :border-left "2px solid #ddd"
                  :padding-left "15px"
                  :margin-bottom "10px"
                  :color "#555"
                  :display "block"}
          [:&.active {:border-color "#666"}]
          [:.time {:color "gray" :margin-right "15px"}]
          [:&.critical {:border-color "pink"}]
          [:&:hover {:background "#f6f6f6"}]
          [:b {:font-weight "300" :color "gray"}]
          [:.fa {:color "gray"  :margin-right "10px"}]
          [:.title {:margin "0px"}]]

         [:.option {:font-weight 300 :color "#888" :margin-right "15px"}]
         ])
       [patient-bgt opened]


       [:br]
       [:h3 "Timeline "
        [:span.search-icon [wgt/icon :search]] [:input.search {:type "search" :placeholder "search..."}]]

       [:span.option [wgt/icon :check-square-o] " Some"]
       [:span.option [wgt/icon :check-square-o] " Filters"]
       [:span.option [wgt/icon :check-square-o] " Teams"]

       [:br]
       [:br]
       [:div.timeline
        [:div.item
         [:span.time "10:30"]
         [wgt/rand-icon]
         "Patient checked in"]

        [:div.item
         [:span.time "10:30"]
         [wgt/rand-icon]
         "Message from patient:" [:span.mute "Please help me..."]
         (user "Dr. House")
         ]

        [:div.item.active
         [:div.title {:style {:background "#f6f6f6" :padding "5px 0"}}
          [:span.time "10:00"]
          [wgt/rand-icon]
          "Vitals: BP: 180/90 Pulse: 100"
          (user "Some Nurse") [:span {:style {:float "right"}} [wgt/icon :chevron-up]]]
         [:br]
         [:table.table.table-sm
          [:tbody
           [:tr
            [:th "BP:"]
            [:td "190/80"]]
           [:tr
            [:th "Pulse"]
            [:td "100"]]
           [:tr
            [:th "Pulse"]
            [:td "100"]]

           ]
          ]
         [:div
          [:span.action "See Vitals"]
          [:span.action "See Observations"]
          ]
         ]
        [:br]

        [:div.item
         [:span.time "10:00"]
         [wgt/rand-icon]
         "Documents scanned: Lab Tests"]

        [:div.item
         [:span.time "10:00"]
         [wgt/rand-icon]
         "Paitient arrived"]


        [:div.item
         [:span.time "10:30"]
         [wgt/rand-icon]
         "Message from patient:" [:span.mute "Please help me..."]
         (user "Dr. House")
         ]

        [:div.item
         [:span.time "10:00"]
         [wgt/rand-icon]
         "Vitals: BP: 180/90 Pulse: 100"
         (user "Some Nurse")
         ]

        [:div.item
         [:span.time "10:00"]
         [wgt/rand-icon]
         "Documents scanned: Lab Tests"]

        [:div.item
         [:span.time "10:00"]
         [wgt/rand-icon]
         "Paitient arrived"]
        ]

       ])))

(defn profile [params]
  [:div
   (styles/style
    [:.visits
     {:width "900px" :margin "0 auto"}
     [:.mute {:color "#888"}]
     [:h3 [:.fa {:font-size "18px"
                 :z-index "100"
                 :position "relative" :left "20px" :margin-left "20px" :color "gray"}]]
     [:.tbody [(keyword "td:nth(1)") {:padding-left 0}]]
     [:.option {:font-weight 300 :color "#888" :margin-right "15px"}]
     [:.search {:border-top "none"
                :border-right "none"
                :padding-left "24px"
                :position "relative"
                :bottom "-1px"
                :width "20em"
                :border-bottom "1px solid #888"
                :border-left "none"}

      [:&:focus
       {:border-top "none"
        :border-right "none"
        :border-bottom "1px solid blue"
        :border-left "none"}]]
     [:img.avatar {:width "18px"
                   :height "18px"
                   :margin "0 5px"
                   :border "1px solid #ddd"
                   :display "inline-block"
                   :border-radius "50%"}]
     {:border-left-color "#4CAF50"}
     [:h3 {:border-bottom "1px solid rgba(0,0,0,.15)!important"
           :font-size "20px"
           :line-height "40px"}]
     [:.item {:display "block" :padding "5px 0" }
      [:.fa {:width "24px"}]]])
   [:div.visits
    [:h3 "People  " [wgt/icon :search] [:input.search {:type "search" :placeholder "search..."}]]
    [:span.option [wgt/icon :check-square-o] " Patients"]
    [:span.option [wgt/icon :check-square-o] " Colleagues"]
    [:span.option [wgt/icon :check-square-o] " Teams"]
    [:br]
    [:br]
    [:table.table
     [:tbody
      [:tr
       [:td
        [:span.action [wgt/icon :thumb-tack]]
        (user "Luis Borhes")]
       [:td [:a.action "Chat"] [:a.action "Task"] [:a.action "Call"]]]
      [:tr
       [:td
        [:span.action [wgt/icon :thumb-tack]]
        (user "Luis Borhes")]
       [:td [:a.action "Chat"] [:a.action "Task"] [:a.action "Call"]]]

      [:tr
       [:td
        [:span.action [wgt/icon :thumb-tack]]
        [:a.assignee  [wgt/icon :users] "Physicians " (avatar) (avatar) (avatar) " ..."]]
       [:td [:a.action "Chat"] [:a.action "Task"] [:a.action "Call"]]]

      [:tr
       [:td
        [:span.action [wgt/icon :thumb-tack]]
        (user "Luis Borhes")]
       [:td [:a.action "Chat"] [:a.action "Task"] [:a.action "Call"]]]

      [:tr
       [:td
        [:span.action [wgt/icon :thumb-tack]]
        (user "Luis Borhes") " Physician"]
       [:td [:a.action "Chat"] [:a.action "Task"] [:a.action "Call"]]
       ]
      [:tr
       [:td
        [:span.action [wgt/icon :thumb-tack]]
        [:a.assignee  [wgt/icon :users] "Physicians " (avatar) (avatar) (avatar) " ..."]]
       [:td [:a.action "Chat"] [:a.action "Task"] [:a.action "Call"]]]
      [:tr
       [:td
        [:span.action [wgt/icon :thumb-tack]]
        (user "Luis Borhes") " Physician"]
       [:td [:a.action "Chat"] [:a.action "Task"] [:a.action "Call"]]
       ]

      ]]]
   ])

(pages/reg-page :dashboard/index index)
(pages/reg-page :dashboard/tasks tasks)
(pages/reg-page :dashboard/notes notes)
(pages/reg-page :dashboard/task task)
(pages/reg-page :dashboard/navigation navigation)
(pages/reg-page :dashboard/patients patients)
(pages/reg-page :dashboard/profile profile)
