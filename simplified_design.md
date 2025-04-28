# 几何学习应用简化设计

## 简化架构

由于数据是预先定义的固定数据，不涉及复杂的增删改查操作，我们可以采用更简单的架构：

1. **数据模型**：定义基本的数据实体类
2. **业务逻辑**：实现核心功能和绘图逻辑
3. **用户界面**：提供交互界面

## 数据实体设计

### 1. 图形（Shape）

```java
/**
 * 图形基类
 */
public abstract class Shape {
    private String name;        // 图形名称
    private String description; // 图形描述
    
    // 绘制图形的抽象方法
    public abstract void draw(Graphics g, int x, int y, int width, int height);
    
    // 构造方法、getter和setter
}

/**
 * 圆形
 */
public class Circle extends Shape {
    // 实现绘制方法
    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.drawOval(x, y, width, height);
    }
}

/**
 * 三角形
 */
public class Triangle extends Shape {
    // 实现绘制方法
    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        int[] xPoints = {x + width/2, x, x + width};
        int[] yPoints = {y, y + height, y + height};
        g.drawPolygon(xPoints, yPoints, 3);
    }
}

/**
 * 正方形
 */
public class Square extends Shape {
    // 实现绘制方法
    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.drawRect(x, y, width, width);
    }
}

/**
 * 长方形
 */
public class Rectangle extends Shape {
    // 实现绘制方法
    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.drawRect(x, y, width, height);
    }
}
```

### 2. 角度（Angle）

```java
/**
 * 角度类
 */
public class Angle {
    private String name;        // 角度名称
    private String description; // 角度描述
    private double value;       // 角度值（度数）
    
    // 绘制角度
    public void draw(Graphics g, int x, int y, int size) {
        // 绘制角度的实现代码
        int endX = (int)(x + size * Math.cos(Math.toRadians(value)));
        int endY = (int)(y - size * Math.sin(Math.toRadians(value)));
        
        g.drawLine(x, y, x + size, y); // 水平线
        g.drawLine(x, y, endX, endY);  // 角度线
    }
    
    // 判断角度类型
    public String getAngleType() {
        if (value == 90) {
            return "直角";
        } else if (value < 90) {
            return "锐角";
        } else if (value < 180) {
            return "钝角";
        } else {
            return "其他角";
        }
    }
    
    // 构造方法、getter和setter
}
```

### 3. 练习（Exercise）

```java
/**
 * 练习类
 */
public class Exercise {
    private String question;      // 问题
    private String[] options;     // 选项
    private int correctAnswer;    // 正确答案索引
    private String explanation;   // 解释
    
    // 检查答案
    public boolean checkAnswer(int answer) {
        return answer == correctAnswer;
    }
    
    // 构造方法、getter和setter
}
```

### 4. 学习进度（StudentProgress）

```java
/**
 * 学习进度类
 */
public class StudentProgress {
    private String studentName;     // 学生姓名
    private int totalAnswered;      // 总回答数
    private int correctAnswers;     // 正确回答数
    
    // 计算正确率
    public double getAccuracy() {
        if (totalAnswered == 0) return 0;
        return (double) correctAnswers / totalAnswered * 100;
    }
    
    // 更新进度
    public void update(boolean isCorrect) {
        totalAnswered++;
        if (isCorrect) {
            correctAnswers++;
        }
    }
    
    // 构造方法、getter和setter
}
```

## 简化目录结构

```
GeometryLearning/
├── src/
│   ├── com/
│   │   └── geometry/
│   │       ├── model/         # 数据模型
│   │       │   ├── Shape.java         # 图形基类
│   │       │   ├── Circle.java        # 圆形
│   │       │   ├── Triangle.java      # 三角形
│   │       │   ├── Square.java        # 正方形
│   │       │   ├── Rectangle.java     # 长方形
│   │       │   ├── Angle.java         # 角度
│   │       │   ├── Exercise.java      # 练习
│   │       │   └── StudentProgress.java # 学习进度
│   │       │
│   │       ├── util/          # 工具类
│   │       │   ├── ShapeFactory.java  # 图形工厂
│   │       │   └── DrawingUtil.java   # 绘图工具
│   │       │
│   │       ├── ui/            # 用户界面
│   │       │   ├── MainFrame.java     # 主窗口
│   │       │   ├── ShapePanel.java    # 图形面板
│   │       │   ├── AnglePanel.java    # 角度面板
│   │       │   ├── ExercisePanel.java # 练习面板
│   │       │   └── ProgressPanel.java # 进度面板
│   │       │
│   │       └── GeometryLearningApp.java # 应用主类
│   │
│   └── resources/             # 资源文件
│       ├── images/            # 图像资源
│       └── data/              # 预定义数据
│           ├── shapes.txt     # 图形数据
│           ├── angles.txt     # 角度数据
│           └── exercises.txt  # 练习数据
│
└── README.md                  # 项目说明
```

## 数据存储

由于数据是固定的，我们可以直接在代码中定义，或者使用简单的文本文件存储预定义数据：

### shapes.txt 示例
```
Circle,圆形,圆形是一种没有棱角的图形，从中心点到边缘的距离都相等。
Triangle,三角形,三角形是由三条线段组成的多边形，有三个角和三条边。
Square,正方形,正方形是一种特殊的四边形，四条边都相等且四个角都是直角。
Rectangle,长方形,长方形是一种四边形，对边平行且相等，四个角都是直角。
```

### angles.txt 示例
```
直角,90,直角等于90度，形状像字母"L"。
锐角,45,锐角小于90度，比直角小。
钝角,135,钝角大于90度但小于180度，比直角大。
平角,180,平角等于180度，形成一条直线。
```

### exercises.txt 示例
```
下面哪个是圆形？,圆形;三角形;正方形;长方形,0,圆形没有角和边，是一种曲线图形。
三角形有几个角？,2;3;4;5,1,三角形有3个角和3条边。
直角等于多少度？,45度;60度;90度;180度,2,直角等于90度。
```

## 数据加载

通过简单的文件读取工具类加载数据：

```java
/**
 * 数据加载工具
 */
public class DataLoader {
    
    // 加载图形数据
    public static List<Shape> loadShapes() {
        // 从文件或代码中加载预定义的图形数据
        List<Shape> shapes = new ArrayList<>();
        // 加载逻辑
        return shapes;
    }
    
    // 加载角度数据
    public static List<Angle> loadAngles() {
        // 从文件或代码中加载预定义的角度数据
        List<Angle> angles = new ArrayList<>();
        // 加载逻辑
        return angles;
    }
    
    // 加载练习数据
    public static List<Exercise> loadExercises() {
        // 从文件或代码中加载预定义的练习数据
        List<Exercise> exercises = new ArrayList<>();
        // 加载逻辑
        return exercises;
    }
}
``` 