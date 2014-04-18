package samantha.app;

public class NavigationHelper
{
    public Class getNextClass(Class defaultClassName)
    {
        if (SamanthaApplication.getInstance().isReady())
        {
            return defaultClassName;
        }
        else
        {
            return StartUp.class;
        }
    }

    public Class getDefaultIntentIfAppIsReady()
    {
        return MainActivity.class;
    }
}
