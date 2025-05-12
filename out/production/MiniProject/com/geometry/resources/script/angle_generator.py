import matplotlib.pyplot as plt
import numpy as np
import os
import math
from matplotlib.patches import Arc, Rectangle, FancyArrowPatch, Circle, Polygon

# 创建输出目录
output_dir = 'angles'
if not os.path.exists(output_dir):
    os.makedirs(output_dir)

def create_angle_image(angle_degrees, filename, figsize=(5, 5), dpi=100):
    """创建并保存角度图片
    
    参数:
        angle_degrees: 角度值(0-360)
        filename: 输出文件名
        figsize: 图片尺寸
        dpi: 分辨率
    """
    # 将角度转换为弧度
    angle_rad = math.radians(angle_degrees)
    
    # 创建图形
    fig, ax = plt.subplots(figsize=figsize)
    
    # 设置坐标轴范围
    ax.set_xlim(-1.5, 1.5)
    ax.set_ylim(-1.5, 1.5)
    
    # 隐藏坐标轴
    ax.axis('off')
    
    # 设置背景颜色 - 淡蓝色背景，与示例图片一致
    ax.set_facecolor('#e6f2f5')
    
    # 定义线条颜色和宽度 - 与示例图片一致
    line_color = '#4682b4'  # 钢蓝色
    arc_color = '#b0d0e0'   # 浅蓝色
    line_width = 2.5
    
    # 确定角度类型并相应绘制
    if angle_degrees == 0 or angle_degrees == 360:  # 0度或360度
        # 仅绘制水平向右的线
        ax.arrow(0, 0, 1, 0, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        
    elif angle_degrees == 90:  # 直角
        # 绘制水平线与箭头
        ax.arrow(0, 0, 1, 0, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        
        # 绘制垂直线与箭头
        ax.arrow(0, 0, 0, 1, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        
        # 添加直角符号 (小正方形)
        rect = Rectangle((0, 0), 0.15, 0.15, linewidth=0, edgecolor='none', facecolor=arc_color, alpha=0.7)
        ax.add_patch(rect)
        
    elif angle_degrees == 180:  # 平角
        # 绘制双向水平线
        ax.arrow(-1, 0, 1, 0, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        ax.arrow(0, 0, 1, 0, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        
        # 添加中点标记
        circle = Circle((0, 0), 0.05, facecolor=line_color)
        ax.add_patch(circle)
        
        # 添加半圆弧线
        arc = Arc((0, 0), 0.4, 0.4, theta1=0, theta2=180, linewidth=1.5, color=line_color)
        ax.add_patch(arc)
        
    elif 0 < angle_degrees < 90:  # 锐角
        # 绘制水平线与箭头
        ax.arrow(0, 0, 1, 0, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        
        # 绘制倾斜线与箭头
        end_x = math.cos(angle_rad)
        end_y = math.sin(angle_rad)
        ax.arrow(0, 0, end_x, end_y, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        
        # 添加角度弧
        wedge = Polygon(
            [[0, 0]] + 
            [[0.25 * math.cos(math.radians(theta)), 0.25 * math.sin(math.radians(theta))] for theta in range(0, int(angle_degrees) + 1, 2)],
            closed=True, facecolor=arc_color, alpha=0.7, edgecolor='none'
        )
        ax.add_patch(wedge)
        
    elif 90 < angle_degrees < 180:  # 钝角
        # 绘制水平线与箭头
        ax.arrow(0, 0, 1, 0, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        
        # 绘制倾斜线与箭头
        end_x = math.cos(angle_rad)
        end_y = math.sin(angle_rad)
        ax.arrow(0, 0, end_x, end_y, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        
        # 添加角度弧
        wedge = Polygon(
            [[0, 0]] + 
            [[0.25 * math.cos(math.radians(theta)), 0.25 * math.sin(math.radians(theta))] for theta in range(0, int(angle_degrees) + 1, 2)],
            closed=True, facecolor=arc_color, alpha=0.7, edgecolor='none'
        )
        ax.add_patch(wedge)
        
    elif 180 < angle_degrees < 360:  # 优角/反射角
        # 绘制水平线与箭头
        ax.arrow(0, 0, 1, 0, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        
        # 绘制倾斜线与箭头
        end_x = math.cos(angle_rad)
        end_y = math.sin(angle_rad)
        ax.arrow(0, 0, end_x, end_y, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        
        # 为反射角添加大圆弧
        # 使用两个部分：从0到180度的弧和从180到angle_degrees的弧
        if angle_degrees <= 270:
            # 方法1: 使用两个半透明填充区域
            wedge1 = Polygon(
                [[0, 0]] + 
                [[0.25 * math.cos(math.radians(theta)), 0.25 * math.sin(math.radians(theta))] for theta in range(0, 181, 2)],
                closed=True, facecolor=arc_color, alpha=0.5, edgecolor='none'
            )
            wedge2 = Polygon(
                [[0, 0]] + 
                [[0.25 * math.cos(math.radians(theta)), 0.25 * math.sin(math.radians(theta))] for theta in range(180, int(angle_degrees) + 1, 2)],
                closed=True, facecolor=arc_color, alpha=0.5, edgecolor='none'
            )
            ax.add_patch(wedge1)
            ax.add_patch(wedge2)
        else:
            # 方法2: 绘制一个完整的环形并在中间有一条线
            circle = Circle((0, 0), 0.25, facecolor=arc_color, alpha=0.4, edgecolor='none')
            ax.add_patch(circle)
            # 添加一个白色的楔形以表示未被包含的角度部分
            wedge_not_included = Polygon(
                [[0, 0]] + 
                [[0.25 * math.cos(math.radians(theta)), 0.25 * math.sin(math.radians(theta))] for theta in range(int(angle_degrees), 361, 2)],
                closed=True, facecolor='white', alpha=1.0, edgecolor='none'
            )
            ax.add_patch(wedge_not_included)
            
            # 绘制从原点到圆弧边缘的线，表示角度的起点和终点
            ax.plot([0, 0.25], [0, 0], color=line_color, linestyle='-', linewidth=1)
            ax.plot([0, 0.25 * math.cos(angle_rad)], [0, 0.25 * math.sin(angle_rad)], color=line_color, linestyle='-', linewidth=1)
    # 保存图片
    plt.savefig(filename, dpi=dpi, bbox_inches='tight', transparent=False)
    plt.close(fig)

# 生成36个角度图片 (0-350度，每10度一个)
for degree in range(0, 360, 10):
    filename = f"{output_dir}/angle_{degree}.png"
    create_angle_image(degree, filename)
    print(f"已生成 {degree}度 角度图片")

print("\n所有角度图片生成完成！") 