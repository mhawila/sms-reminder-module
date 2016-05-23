package org.openmrs.module.smsreminder.utils;

import org.openmrs.Obs;
import org.openmrs.api.EncounterService;
import org.openmrs.api.ObsService;
import org.openmrs.api.context.Context;

/*
 * 
 * @author : Nelson Mahumane
 *
*/
public class SMSClient implements Runnable{

  public final static int SYNCHRONOUS=0;
  public final static int ASYNCHRONOUS=1;
  private Thread myThread=null;

  private int mode=-1;
  private String recipient=null;
  private String message=null;
  private String smsc;
  private String porta;

  public int status=-1;
  public long messageNo=-1;


  public SMSClient(int mode) {
      this.mode=mode;
    }

  public int sendMessage (String smsc,String porta,String recipient, String message){
    this.recipient=recipient;
    this.message=message;
    this.porta=porta;
    this.smsc=smsc;
    //System.out.println("recipient: " + recipient + " message: " + message);
    myThread = new Thread(this);
    myThread.start();
   //run();
    //ObsService obsService = Context.getObsService();



    //EncounterService encounterService=Context.getEncounterService();

    return status;
    }
    public void run(){

    Sender aSender = new Sender(recipient,message,smsc,porta);

    try{
      //send message
          aSender.send ();

         // System.out.println("sending ... ");

      //in SYNCHRONOUS mode wait for return : 0 for OK, -2 for timeout, -1 for other errors
      if (mode==SYNCHRONOUS) {
          while (aSender.status == -1){
            myThread.sleep (5000);
          }
      }
      if (aSender.status == 0) messageNo=aSender.messageNo ;

    }catch (Exception e){

        e.printStackTrace();

    }

    this.status=aSender.status ;

    aSender=null;


  }
}