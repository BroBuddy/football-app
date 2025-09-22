import { useState, ReactNode } from 'react';

export interface Tab {
  title: string;
  content: ReactNode;
}

interface TabsProps {
  tabs: Tab[];
}

export const Tabs = ({ tabs }: TabsProps) => {
  const [activeTab, setActiveTab] = useState(0);

  return (
    <div className="flex flex-col">
      <div className="flex items-center border justify-around bg-black rounded p-2 m-b-4">
        {tabs.map((tab, index) => (
          <span
            key={index}
            className={`flex text-sm p-x-5 cursor ${index === activeTab ? 'text-white text-bold' : 'text-grey-300'}`}
            onClick={() => setActiveTab(index)}
          >
            {tab.title}
          </span>
        ))}
      </div>

      {tabs[activeTab].content}
    </div>
  );
};