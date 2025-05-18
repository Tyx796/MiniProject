import matplotlib.pyplot as plt
import numpy as np
import os
import math
from matplotlib.patches import Arc, Rectangle, FancyArrowPatch, Circle, Polygon

# Create output directory
output_dir = 'angles'
if not os.path.exists(output_dir):
    os.makedirs(output_dir)

def create_angle_image(angle_degrees, filename, figsize=(5, 5), dpi=100):
    """Create and save an angle image
    
    Parameters:
        angle_degrees: Angle value (0-360)
        filename: Output file name
        figsize: Image size
        dpi: Resolution
    """
    # Convert angle to radians
    angle_rad = math.radians(angle_degrees)
    
    # Create figure
    fig, ax = plt.subplots(figsize=figsize)
    
    # Set coordinate axis range
    ax.set_xlim(-1.5, 1.5)
    ax.set_ylim(-1.5, 1.5)
    
    # Hide axes
    ax.axis('off')
    
    # Set background color - light blue background, consistent with example image
    ax.set_facecolor('#e6f2f5')
    
    # Define line colors and widths - consistent with example image
    line_color = '#4682b4'  # Steel blue
    arc_color = '#b0d0e0'   # Light blue
    line_width = 2.5
    
    # Determine angle type and draw accordingly
    if angle_degrees == 0 or angle_degrees == 360:  # 0 degree or 360 degree
        # Draw only horizontal line pointing right
        ax.arrow(0, 0, 1, 0, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        
    elif angle_degrees == 90:  # Right angle
        # Draw horizontal line with arrow
        ax.arrow(0, 0, 1, 0, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        
        # Draw vertical line with arrow
        ax.arrow(0, 0, 0, 1, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        
        # Add right angle symbol (small square)
        rect = Rectangle((0, 0), 0.15, 0.15, linewidth=0, edgecolor='none', facecolor=arc_color, alpha=0.7)
        ax.add_patch(rect)
        
    elif angle_degrees == 180:  # Straight angle
        # Draw bidirectional horizontal line
        ax.arrow(-1, 0, 1, 0, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        ax.arrow(0, 0, 1, 0, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        
        # Add midpoint mark
        circle = Circle((0, 0), 0.05, facecolor=line_color)
        ax.add_patch(circle)
        
        # Add semicircle arc
        arc = Arc((0, 0), 0.4, 0.4, theta1=0, theta2=180, linewidth=1.5, color=line_color)
        ax.add_patch(arc)
        
    elif 0 < angle_degrees < 90:  # Acute angle
        # Draw horizontal line with arrow
        ax.arrow(0, 0, 1, 0, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        
        # Draw angled line with arrow
        end_x = math.cos(angle_rad)
        end_y = math.sin(angle_rad)
        ax.arrow(0, 0, end_x, end_y, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        
        # Add angle arc
        wedge = Polygon(
            [[0, 0]] + 
            [[0.25 * math.cos(math.radians(theta)), 0.25 * math.sin(math.radians(theta))] for theta in range(0, int(angle_degrees) + 1, 2)],
            closed=True, facecolor=arc_color, alpha=0.7, edgecolor='none'
        )
        ax.add_patch(wedge)
        
    elif 90 < angle_degrees < 180:  # Obtuse angle
        # Draw horizontal line with arrow
        ax.arrow(0, 0, 1, 0, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        
        # Draw angled line with arrow
        end_x = math.cos(angle_rad)
        end_y = math.sin(angle_rad)
        ax.arrow(0, 0, end_x, end_y, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        
        # Add angle arc
        wedge = Polygon(
            [[0, 0]] + 
            [[0.25 * math.cos(math.radians(theta)), 0.25 * math.sin(math.radians(theta))] for theta in range(0, int(angle_degrees) + 1, 2)],
            closed=True, facecolor=arc_color, alpha=0.7, edgecolor='none'
        )
        ax.add_patch(wedge)
        
    elif 180 < angle_degrees < 360:  # Reflex angle
        # Draw horizontal line with arrow
        ax.arrow(0, 0, 1, 0, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        
        # Draw angled line with arrow
        end_x = math.cos(angle_rad)
        end_y = math.sin(angle_rad)
        ax.arrow(0, 0, end_x, end_y, head_width=0.08, head_length=0.1, fc=line_color, ec=line_color, linewidth=line_width)
        
        # Add large arc for reflex angle
        # Use two parts: arc from 0 to 180 degrees and arc from 180 to angle_degrees
        if angle_degrees <= 270:
            # Method 1: Use two semi-transparent filled areas
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
            # Method 2: Draw a complete ring with a line in the middle
            circle = Circle((0, 0), 0.25, facecolor=arc_color, alpha=0.4, edgecolor='none')
            ax.add_patch(circle)
            # Add a white wedge to represent the non-included angle portion
            wedge_not_included = Polygon(
                [[0, 0]] + 
                [[0.25 * math.cos(math.radians(theta)), 0.25 * math.sin(math.radians(theta))] for theta in range(int(angle_degrees), 361, 2)],
                closed=True, facecolor='white', alpha=1.0, edgecolor='none'
            )
            ax.add_patch(wedge_not_included)
            
            # Draw lines from origin to circle edge, representing angle start and end points
            ax.plot([0, 0.25], [0, 0], color=line_color, linestyle='-', linewidth=1)
            ax.plot([0, 0.25 * math.cos(angle_rad)], [0, 0.25 * math.sin(angle_rad)], color=line_color, linestyle='-', linewidth=1)
    # Save image
    plt.savefig(filename, dpi=dpi, bbox_inches='tight', transparent=False)
    plt.close(fig)

# Generate 36 angle images (0-350 degrees, at 10-degree intervals)
for degree in range(0, 360, 10):
    filename = f"{output_dir}/angle_{degree}.png"
    create_angle_image(degree, filename)
    print(f"Generated {degree} degree angle image")

print("\nAll angle images generated successfully!") 