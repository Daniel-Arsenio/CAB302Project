package src.Model.Interfaces;

public interface ICell {
    int topEdge = 2;
    int rightEdge = 2;
    int bottomEdge = 2;
    int leftEdge = 2;

    void BreakCellWall(String target);

    void AddCellWall(String target);

    boolean IsReachable(String target);

    void markTracedBack();

    void markTraveled();

    void removeMark();

    void gainCellFocus();

    void dropCellFocus();
}
