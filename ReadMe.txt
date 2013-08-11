Grid Computing Systems

Grid Computing System A consists of a job queue and two compute servers.

Initially, the job queue is empty and both compute servers are idle. Jobs arrive in the system and go into the job queue in FIFO order. Whenever one of the compute servers is idle and the job queue is not empty, the compute server removes the first job from the job queue, becomes busy, and proceeds to execute the job. When the job finishes, the compute server sends the job results and becomes idle again.

Grid Computing System A has the following characteristics:

    The job interarrival times are exponentially distributed with a certain mean interarrival time.

    The job execution times are exponentially distributed with a certain mean execution time, the same for both compute servers.

    The job queue can hold any number of jobs; there is no maximum job queue size.

    A job's response time is the time from the instant when the job goes into the job queue until the instant when the job finishes executing. 

Grid Computing System B consists of a job queue and four compute servers, but otherwise operates the same as Grid Computing System A.

Investigation

You must do the following:

    Formulate a testable hypothesis concerning the mean job response time in Grid Computing System A as compared to the mean job response time in Grid Computing System B.

    Write a simulation program or programs to investigate the hypothesis.

    Execute the simulation program(s) to collect data. You may choose the mean job execution time as you please (the same in all compute servers in both systems). You must investigate a range of mean job interarrival times; you may choose the lower and upper bounds of the mean job interarrival times as you please.

    Analyze the data to determine whether the hypothesis is true or false.

    Submit a written report with the results of your investigation. 