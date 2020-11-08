package gui;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class OverlapLayout implements LayoutManager2, java.io.Serializable
{
	public static Boolean POP_UP = Boolean.TRUE;
	public static Boolean POP_DOWN = Boolean.FALSE;

	private static final int PREFERRED = 0;
	private static final int MINIMUM = 1;
	private boolean overlapAbove;

	private Point overlapPosition;

	//  Reserve space for invisible components in the Container
	private boolean includeInvisible = true;

	//  Reserve extra space so a component can "popup"
	private Insets popupInsets = new Insets(0, 0, 0, 0);

	//  Track original order in which the components where added
	private List<Component> components = new ArrayList<Component>();

	//  Track a constraint added to a component
	private HashMap<Component, Boolean> constraints = new HashMap<Component, Boolean>();

	//  Track maximum dimension of any component for easier layout
	private Dimension maximumSize = new Dimension();

	public OverlapLayout()
	{
		this( new Point(0, 0) );
	}

	public OverlapLayout(Point overlapPosition)
	{
		this(overlapPosition, true);
	}

	public OverlapLayout(Point overlapPosition, boolean overlapAbove)
	{
		setOverlapPosition( overlapPosition );
		this.overlapAbove = overlapAbove;
	}

	public int convertIndex(int index)
	{
		if (overlapAbove)
			return components.size() - index - 1;
		else
			return index;
	}

	public boolean isIncludeInvisible()
	{
		return includeInvisible;
	}

	public void	setIncludeInvisible(boolean includeInvisible)
	{
		this.includeInvisible = includeInvisible;
	}

	public Point getOverlapPosition()
	{
		return overlapPosition;
	}

	public void setOverlapPosition(Point overlapPosition)
	{
		this.overlapPosition = overlapPosition;
	}

	public Insets getPopupInsets()
	{
		return popupInsets;
	}

	public void setPopupInsets(Insets popupInsets)
	{
		this.popupInsets = popupInsets;
	}

    public Boolean getConstraints(Component component)
    {
    	return (Boolean)constraints.get(component);
    }

	public void addLayoutComponent(String name, Component comp) {}

	public void addLayoutComponent(Component component, Object constraint)
	{
		if (constraint == null)
		{
			constraints.remove(component);
		}
		else if (constraint instanceof Boolean)
		{
			constraints.put(component, (Boolean)constraint);
		}
		else
		{
			String message = "Constraint parameter must be of type Boolean";
			throw new IllegalArgumentException( message );
		}
		
		if (!components.contains(component))
		{
			Container parent = component.getParent();
			int size = parent.getComponentCount();

			for (int i = 0 ; i < size ; i++)
			{
				Component c = parent.getComponent(i);

				if (c == component)
				{
					components.add(i, component);

					if (overlapAbove)
					{
						parent.setComponentZOrder(component, size - i - 1);
					}

		  	  		break;
				}
			}
		}
	}

	public void removeLayoutComponent(Component component)
	{
		components.remove( component );
		constraints.remove( component );
	}

	public Dimension minimumLayoutSize(Container parent)
	{
		synchronized (parent.getTreeLock())
		{
			return getLayoutSize(parent, MINIMUM);
		}
	}

	public Dimension preferredLayoutSize(Container parent)
	{
		synchronized (parent.getTreeLock())
		{
			return getLayoutSize(parent, PREFERRED);
		}
	}

	private Dimension getLayoutSize(Container parent, int type)
	{
		int visibleComponents = 0;
		int width = 0;
		int height = 0;

		//  All components will be resized to the maximum dimension

		for (Component component: components)
		{
			if (component.isVisible()
			||  includeInvisible)
			{
				Dimension size = getDimension(component, type);
				width = Math.max(size.width, width);
				height = Math.max(size.height, height);
				visibleComponents++;
			}
		}

		if (visibleComponents == 0)
			return new Dimension(0, 0);

		//  Keep maximum dimension for easy access when laying out components

		if  (type == PREFERRED)
		{
			maximumSize.width = width;
			maximumSize.height = height;
		}

		//  Adjust size for each overlapping component

		visibleComponents--;
		width  += visibleComponents * Math.abs(overlapPosition.x);
		height += visibleComponents * Math.abs(overlapPosition.y);

		//  Adjust for parent Container and popup insets

		Insets parentInsets = parent.getInsets();
		width += parentInsets.left + parentInsets.right;
		height += parentInsets.top + parentInsets.bottom;

		width += popupInsets.left + popupInsets.right;
		height += popupInsets.top + popupInsets.bottom;

		return new Dimension(width, height);
	}

	private Dimension getDimension(Component component, int type)
	{
		switch (type)
		{
			case PREFERRED: return component.getPreferredSize();
			case MINIMUM: return component.getMinimumSize();
			default: return new Dimension(0, 0);
		}
	}

	public void layoutContainer(Container parent)
	{
	synchronized (parent.getTreeLock())
	{
		int size = components.size();

		if (size == 0) return;

		//  Determine location of first component

		Point location = new Point(0, 0);
		Insets parentInsets = parent.getInsets();

		//  Layout right-to-left, else left-to-right

		if (overlapPosition.x < 0)
			location.x = parent.getWidth() - maximumSize.width - parentInsets.right - popupInsets.right;
		else
			location.x = parentInsets.left + popupInsets.left;

		//  Layout bottom-to-top, else top-to-bottom

		if (overlapPosition.y < 0)
			location.y = parent.getHeight() - maximumSize.height - parentInsets.bottom - popupInsets.bottom;
		else
			location.y = parentInsets.top + popupInsets.top;

		//  Set the size and location for each component

		for (int i = 0 ; i < size ; i++)
		{
			Component component = components.get(i);

			if (component.isVisible()
			||  includeInvisible)
			{

				if (overlapPosition.x == 0 && overlapPosition.y == 0)
				{
					int width = parent.getWidth() - parentInsets.left - parentInsets.right;
					int height = parent.getHeight() - parentInsets.top - parentInsets.bottom;
					component.setSize(width, height);
				}
				else  //  resize each component to be the same size
				{
					component.setSize( maximumSize );
				}

				//  Set location of the component

				int x = location.x;
				int y = location.y;

				//  Adjust location when component is "popped up"

				Boolean constraint = constraints.get(component);

				if (constraint != null
				&&  constraint == Boolean.TRUE)
				{
					x += popupInsets.right - popupInsets.left;
					y += popupInsets.bottom - popupInsets.top;
				}

				component.setLocation(x, y);

				//  Calculate location of next component using the overlap offsets

				location.x += overlapPosition.x;
				location.y += overlapPosition.y;
			}
		}
	}}


	public Dimension maximumLayoutSize(Container target)
	{
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	public float getLayoutAlignmentX(Container parent)
	{
		return 0.5f;
	}

	public float getLayoutAlignmentY(Container parent)
	{
		return 0.5f;
	}

	public void invalidateLayout(Container target)
	{
		// remove constraints here?
	}

	public String toString()
	{
		return getClass().getName()
			+ "[overlapAbove=" + overlapAbove
			+ ",overlapPosition=" + overlapPosition
			+ "]";
	}
}
