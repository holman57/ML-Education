# ML Education

Using Logistic Regression as a Proficiency Model

- **Mean problems done to reach proficiency:** ideally we like to minimize this so that students can spend less time rote grinding on problems they know well, and move on to other concepts.
- **P(next problem correct | just gained proficiency):** Unfortunately, this is hard to correctly measure in our offline data set due to the streak-of-10 bias: students may loosen up after they gain proficiency and spend less time on subsequent problems.
- **Proficiency Rate:** The percent of proficiencies attained per user-exercise pair. Again, this is hard to measure because of the streak bias.
- **Confusion matrix for predicted next problem correct:** This is for comparing binary classifiers on their accuracy in predicting the outcome of any answer in a user’s response history. We build up the confusion matrix, and from that extract two valuable measures of the performance of a binary classifier.

- **Metric gathering**
  - Response time
  - Short and Long term memory
    - **Retention**
  - Background color transitions
  - Sounds generated
  - The speed of deliver
  - Proficiencies earned per user
  - New exercises attempted per user
  - Problems done per proficiency
  - Accuracy: **P(next problem correct | just gained proficiency)**
- **NLP**
  - Chatbot response to encouragement and praise
  - Analysis of positive reinforcement 
  - Discouragement analysis

Let’s say we have the values of  input features, and we stuff them into a vector **x**. Let’s say we also happen to know how much each feature makes it more likely that the user is proficient, and stuff those weights into a vector **w**. We can then take the weighted sum of the input features, plus a pre-determined constant  to correct for any constant bias, and call that **z**:

![logisitc_reg.png](https://raw.githubusercontent.com/holman57/MLeducation/master/logisitc_reg.png)

- A/B Testing
- Logistic Regression

A/Bingo split testing now on App Engine, built for Khan Academy
https://bjk5.com/post/10171483254/abingo-split-testing-now-on-app-engine-built-for

Khan Academy Exercises
https://github.com/Khan/khan-exercises

Sensitivity and specificity
https://en.wikipedia.org/wiki/Sensitivity_and_specificity 

Least Squares Fitting--Exponential
http://mathworld.wolfram.com/LeastSquaresFittingExponential.html
To fit a functional form

![Life-cycle Diagram](https://raw.githubusercontent.com/holman57/MLeducation/master/Life-cycle%20Diagram.png)

![Language Use-Cases](https://raw.githubusercontent.com/holman57/MLeducation/master/Language-Use-Cases.png)

![Mathematics Use-Cases](https://raw.githubusercontent.com/holman57/MLeducation/master/Math-Use-Cases.png)



hiteshsahu/Android-TTS-STT

One line solution for Android Text to speech(TTS) & Speech to Text(STT) translation problem https://github.com/hiteshsahu/Android-TTS-STT.git

animated-gradient-background-tutorial
Gradient Background

Animated Gradient Background in Android :-  http://www.androidtutorialshub.com/animated-gradient-background-in-android/

---

# Research

Curriculum for Reinforcement Learning
- https://lilianweng.github.io/lil-log/2020/01/29/curriculum-for-reinforcement-learning.html

      @article{weng2020curriculum,
        title   = "Curriculum for Reinforcement Learning",
        author  = "Weng, Lilian",
        journal = "lilianweng.github.io/lil-log",
        year    = "2020",
        url     = "https://lilianweng.github.io/lil-log/2020/01/29/curriculum-for-reinforcement-learning.html"
      }

Learning Curriculum Policies for Reinforcement Learning
- https://arxiv.org/abs/1812.00285
