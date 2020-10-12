package dialog.pm;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * @author Chen Jiongyu
 */
public class PmDialog {
    private static final int PROJECT_IMG_WIDTH = 250;
    private static final double ROW_HEIGHT = 80;
    private static final double HEADER_HEIGHT = 40;
    private static final double ROW_BORDER_WIDTH = 1;
    private final ObservableList<Project> recentProjects;
    private final ObservableList<Project> pinProjects;
    @FXML
    private DialogPane dialogPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vBox;
    @FXML
    private VBox pinPane;
    @FXML
    private VBox recentPane;
    @FXML
    private TableView<Project> pinTable;
    @FXML
    private TableView<Project> recentTable;

    public PmDialog() {
        recentProjects = FXCollections.observableArrayList();
        pinProjects = FXCollections.observableArrayList();
    }

    @FXML
    private void initialize() {
        // TODO when clicked, blue border shows
        dialogPane.getStylesheets().add(getClass().getResource("PmDialog.css").toExternalForm());
        initTable(pinTable);
        initTable(recentTable);
        pinTable.setItems(pinProjects);
        recentTable.setItems(recentProjects);
    }

    @SuppressWarnings("unchecked")
    private void initTable(TableView<Project> table) {
        table.setFixedCellSize(ROW_HEIGHT);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<TableColumn<Project, ?>> columns = table.getColumns();
        TableColumn<Project, Image> imgCol = (TableColumn<Project, Image>) columns.get(0);
        imgCol.setCellFactory(column -> {
            //Set up the ImageView
            final ImageView imageview = new ImageView();
            imageview.setPreserveRatio(true);
            imageview.setFitHeight(ROW_HEIGHT - 10);

            //Set up the Table
            TableCell<Project, Image> cell = new TableCell<Project, Image>() {
                @Override
                public void updateItem(Image item, boolean empty) {
                    if (item != null) {
                        imageview.setImage(item);
                    }
                }
            };
            // Attach the imageview to the cell
            cell.setGraphic(imageview);
            return cell;
        });
        imgCol.setPrefWidth(PROJECT_IMG_WIDTH);
        imgCol.setCellValueFactory(new PropertyValueFactory<>("image"));

        DoubleBinding remain = table.widthProperty().subtract(PROJECT_IMG_WIDTH + 2);

        TableColumn<Project, String> nameCol = (TableColumn<Project, String>) columns.get(1);
        nameCol.prefWidthProperty().bind(remain.multiply(0.3));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Project, String> pathCol = (TableColumn<Project, String>) columns.get(2);
        pathCol.prefWidthProperty().bind(remain.multiply(0.3));
        pathCol.setCellValueFactory(new PropertyValueFactory<>("path"));

        TableColumn<Project, String> createDateCol = (TableColumn<Project, String>) columns.get(3);
        createDateCol.prefWidthProperty().bind(remain.multiply(0.2));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));

        TableColumn<Project, String> modifyDateCol = (TableColumn<Project, String>) columns.get(4);
        modifyDateCol.prefWidthProperty().bind(remain.multiply(0.2));
        modifyDateCol.setCellValueFactory(new PropertyValueFactory<>("modifyDate"));

        // disable reordering columns
        table.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            final TableHeaderRow header = (TableHeaderRow) table.lookup("TableHeaderRow");
            header.reorderingProperty().addListener((o, oldVal, newVal) -> header.setReordering(false));
        });
    }

    public void addRecentProject(Project project) {
        recentProjects.add(project);
        recentTable.prefHeightProperty().unbind();
        recentTable.prefHeightProperty().bind(Bindings.size(recentTable.getItems())
                .multiply(recentTable.getFixedCellSize() + ROW_BORDER_WIDTH).add(HEADER_HEIGHT + ROW_BORDER_WIDTH));
    }

    public void addPinProject(Project project) {
        pinProjects.add(project);
        // TODO 滚动条出现的问题
        pinTable.prefHeightProperty().unbind();
        pinTable.prefHeightProperty().bind(Bindings.size(pinTable.getItems())
                .multiply(pinTable.getFixedCellSize() + ROW_BORDER_WIDTH).add(HEADER_HEIGHT + 20));
    }

    public static class Project {
        private final ObjectProperty<Image> image;
        private final StringProperty name;
        private final StringProperty path;
        private final StringProperty createDate;
        private final StringProperty modifyDate;

        public Project(Image image, String name, String path, String createDate, String modifyDate) {
            this.image = new SimpleObjectProperty<>(image);
            this.name = new SimpleStringProperty(name);
            this.path = new SimpleStringProperty(path);
            this.createDate = new SimpleStringProperty(createDate);
            this.modifyDate = new SimpleStringProperty(modifyDate);
        }

        public Image getImage() {
            return image.get();
        }

        public void setImage(Image image) {
            this.image.set(image);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public String getPath() {
            return path.get();
        }

        public void setPath(String path) {
            this.path.set(path);
        }

        public String getCreateDate() {
            return createDate.get();
        }

        public void setCreateDate(String createDate) {
            this.createDate.set(createDate);
        }

        public String getModifyDate() {
            return modifyDate.get();
        }

        public void setModifyDate(String modifyDate) {
            this.modifyDate.set(modifyDate);
        }
    }
}
