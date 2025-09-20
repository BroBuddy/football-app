export const getPlayerCountText = (totalElements: number) => {
  if (totalElements === 0) {
    return "No Players found";
  } else if (totalElements === 1) {
    return "1 Player found";
  } else {
    return `${totalElements} Players found`;
  }
};

export const formatMarketValue = (value: number): string => {
    if (value >= 1000) {
        return `€${(value / 1000).toFixed(1)}B`;
    }

    return `€${value.toFixed(1)}M`;
};

export const API_URL = import.meta.env.VITE_API_URL;

export const apiClient = {
  fetch: async <T>(endpoint: string, options?: RequestInit): Promise<T> => {
    const url = `${API_URL}${endpoint}`;
    
    try {
      const response = await fetch(url, options);
      
      if (!response.ok) {
        const errorData = await response.json().catch(() => ({}));
        
        const errorMessage = `API Error: ${response.status} ${response.statusText}. Details: ${JSON.stringify(errorData)}`;
        throw new Error(errorMessage);
      }

      const data: T = await response.json();
      return data;
    } catch (error) {
      if (error instanceof Error) {
        if (error.name === 'AbortError') {
          console.log('Fetch request was aborted.');
          throw error;
        }
        console.error("Failed to fetch data:", error);
      }
      throw error;
    }
  },
};