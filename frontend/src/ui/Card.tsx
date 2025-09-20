import * as React from 'react';

const Card: React.FC<React.HTMLAttributes<HTMLDivElement>> = ({ className, children, ...props }) => {
  return (
    <div
      className={`card rounded ${className || ''}`}
      {...props}
    >
      {children}
    </div>
  );
};

const CardHeader: React.FC<React.HTMLAttributes<HTMLDivElement>> = ({ className, children, ...props }) => {
  return (
    <div
      className={`${className || ''}`}
      {...props}
    >
      {children}
    </div>
  );
};

const CardContent: React.FC<React.HTMLAttributes<HTMLDivElement>> = ({ className, children, ...props }) => {
  return (
    <div
      className={`${className || ''}`}
      {...props}
    >
      {children}
    </div>
  );
};

export { Card, CardHeader, CardContent };