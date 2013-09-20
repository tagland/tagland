package org.tagland.retro.gui;

import us.anarchia.obj.StorableComposite;

public class RetroMain {

	public RetroMain() {
        init();
	}

    private StorableComposite context = new StorableComposite();
    public StorableComposite getContext(){
        return context;
    }

    protected void init(){
        StorableComposite el;
        el = new StorableComposite();
        el.setID("DesignerRegistry");
        context.addElement(el);
        el.addAttribute("Author", "us.anarchia.gui.designers.AuthorDesigner");
        el.addAttribute("Author", "us.anarchia.gui.designers.AuthorDesigner");
        el.addAttribute("Author", "us.anarchia.gui.designers.AuthorDesigner");
        el.addAttribute("Author", "us.anarchia.gui.designers.AuthorDesigner");
        /*
        well, no it's really an xml file that describes the fields, setters and getters to
        display in the property sheet, including inherited,
        then you also need a function to tell you how to handle collections.
        Metadata
            Author
               fullName
               Copyright
                   ID
                   copyrightDate
            Frame
                getPanels() :: Panels



                */
    }

	public static void main(String[] args) {
		System.out.println("RetroMain V 1.0");
        RetroMain retro = new RetroMain();
		RetroPanel.factory(retro);
	}
	
}

