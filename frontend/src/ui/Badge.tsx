import React from 'react';

interface BadgeProps {
  className?: string;
  children: React.ReactNode;
}

const Badge: React.FC<BadgeProps> = ({ className, children }) => {
  return (
    <div className={`badge rounded text-sm ${className || ''}`}>
      {children}
    </div>
  );
};

export default Badge;