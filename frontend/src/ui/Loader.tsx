import { Loader2 } from 'lucide-react';
import React from 'react';
import "./Loader.scss";
import { Card, CardHeader } from './Card';

const Loader: React.FC = () => {
  return (
    <Card>
      <CardHeader>
        <Loader2 size={40} className="loader text-white" />
      </CardHeader>
    </Card>
  );
};

export default Loader;